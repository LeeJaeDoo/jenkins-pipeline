import org.jenkinsci.plugins.workflow.libs.Library

import static java.util.concurrent.TimeUnit.SECONDS

@Library('shared') _
/**
 * @author Park So Jeong
 */

node {
    try {
        stage('CHECKOUT') {
            gitCheckout()
        }
//    stage ('CLEAN') {
//        clean()
//    }
        stage('BUILD') {
            build()
        }
//    stage ('TEST') {
//        test()
//    }
        stage('DEPLOY') {
            deploy()
        }
        notifySlack("SUCCESS", "#00FF00")
    } catch (e) {
        notifySlack("FAILED", "#FF0000")
    }
}
def gitCheckout() {
    checkout([$class                           : 'GitSCM',
              branches                         : [[name: "*/master"]],
              doGenerateSubmoduleConfigurations: false,
              extensions                       : [[$class: 'WipeWorkspace'], [$class: 'LocalBranch', localBranch: '**']],
              userRemoteConfigs                : [[credentialsId: 'LeeJaeDoo', url: 'https://LeeJaeDoo:262283b0c7139420fc2eb4f2d2a6b0e8d6fc19d4@github.com/it-chip/sp-fe']]])

    log.withStepNoEnd(stageName: "최근 commit log", {
        sh script: "git log --since=" + lastBuild.diffTime(SECONDS) + ".seconds --pretty=format:'%h %<(10,trunc)%an %ad   %s' --date=format:'%Y-%m-%d %H:%M'"
    })

    log.withStepNoEnd(stageName: "최근 changed log", {
        sh script: "git diff \$(git log -1 --before=@{" + lastBuild.diffTime(SECONDS) + ".seconds.ago} --format=%H) --stat"
    })
}

def build(String subProjectName = "", String additionalOption = "") {
    sh script: "npm install"
    sh script: "npm run build"
}

def deploy(String playbookName = "", String  extras = "") {
    ansiblePlaybook(
            inventory: "/home/ubuntu/ansibles/inventories/hosts/inventory",
            playbook: "/home/ubuntu/ansibles/sp-fe.yml",
            extras: "--ssh-common-args='-o StrictHostKeyChecking=no' "
    )
}
