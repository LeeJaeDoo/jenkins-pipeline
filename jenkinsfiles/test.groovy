import org.jenkinsci.plugins.workflow.libs.Library

import static java.util.concurrent.TimeUnit.SECONDS

@Library('shared') _
/**
 * @author Jaedoo Lee
 */

node {
    stage ('CHECKOUT') {
        gitCheckout()
    }
    stage ('CLEAN') {
        clean()
    }
    stage ('BUILD') {
        build()
    }
    stage ('TEST') {
        test()
    }
    stage ('DEPLOY') {
        deploy()
    }
}

// 함수 선언 (반환 타입이 없기 때문에 void로 선언, 있다면 def로 선언하면 됨)
void print(message) {
    echo "${message}"
}

def gitCheckout() {
    checkout([$class                           : 'GitSCM',
              branches                         : [[name: "*/master"]],
              doGenerateSubmoduleConfigurations: false,
              extensions                       : [[$class: 'WipeWorkspace'], [$class: 'LocalBranch', localBranch: '**']],
              userRemoteConfigs                : [[credentialsId: 'LeeJaeDoo', url: 'https://LeeJaeDoo:fd3ea2c7b6d80a0282f105a3ea2e2acda53b0012@github.com/LeeJaeDoo/sp-member']]])

    log.withStepNoEnd(stageName: "최근 commit log", {
        sh script: "git log --since=" + lastBuild.diffTime(SECONDS) + ".seconds --pretty=format:'%h %<(10,trunc)%an %ad   %s' --date=format:'%Y-%m-%d %H:%M'"
    })

    log.withStepNoEnd(stageName: "최근 changed log", {
        sh script: "git diff \$(git log -1 --before=@{" + lastBuild.diffTime(SECONDS) + ".seconds.ago} --format=%H) --stat"
    })
}

def clean(String subProjectName = "", String additionalOption = "") {
    if (subProjectName.isEmpty()) {
        sh script: "./gradlew clean -q ${additionalOption}"
    } else {
        sh script: "./gradlew :${subProjectName}:clean -q ${additionalOption}"
    }
}

def build(String subProjectName = "", String additionalOption = "") {
    if (subProjectName.isEmpty()) {
        sh script: "./gradlew :build ${additionalOption}"
    } else {
        sh script: "./gradlew :${subProjectName}:build ${additionalOption}"
    }
}

def test(String subProjectName = "", Boolean openapiUsed = false, String additionalOption = "") {
    def extras = ""

    if (openapiUsed && !subProjectName.isEmpty()) {
        def extraVars = [subProjectName: subProjectName]
        sh script: "./gradlew :${subProjectName}:openapi3 ${additionalOption}"

        ansiblePlaybook(
            extraVars: extraVars,
            inventory: "/home/ubuntu/ansibles/inventories/hosts/inventory",
            playbook: "/home/ubuntu/ansibles/ncp_documentation.yml",
            credentialsId: 'LeeJaeDoo',
            extras: "--ssh-common-args='-o StrictHostKeyChecking=no' "
        )
    } else {
        sh script: "./gradlew ${subProjectName}:test ${extras}"
    }
}

def deploy(String playbookName = "", String  extras = "") {
    ansiblePlaybook(
        inventory: "/home/ubuntu/ansibles/inventories/hosts/inventory",
        playbook: "/home/ubuntu/ansibles/test.yml",
        extras: "--ssh-common-args='-o StrictHostKeyChecking=no' "
    )
}
