node {
    /*def hello = 'Hello jojoldu' // 변수선언
    stage ('clone') {
        git 'https://github.com/jojoldu/jenkins-pipeline.git' // git clone
    }
    dir ('sample') { // clone 받은 프로젝트 안의 sample 디렉토리에서 stage 실행
        stage ('sample/execute') {
            sh './execute.sh'
        }
    }
    stage ('print') {
        print(hello) // 함수 + 변수 사용
    }*/
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
              userRemoteConfigs                : [[credentialsId: 'LeeJaeDoo', url: 'https://github.com/LeeJaeDoo/sp-member']]])

//    log.withStepNoEnd(stageName: "최근 commit log", {
//        sh script: "git log --since=" + lastBuild.diffTime(SECONDS) + ".seconds --pretty=format:'%h %<(10,trunc)%an %ad   %s' --date=format:'%Y-%m-%d %H:%M'"
//    })
//
//    log.withStepNoEnd(stageName: "최근 changed log", {
//        sh script: "git diff \$(git log -1 --before=@{" + lastBuild.diffTime(SECONDS) + ".seconds.ago} --format=%H) --stat"
//    })
}

def clean(String subProjectName = "", String additionalOption = "") {
    if (subProjectName.isEmpty()) {
        sh script: "gradle :clean -q ${additionalOption}"
    } else {
        sh script: "gradle :${subProjectName}:clean -q ${additionalOption}"
    }
}

def build(String subProjectName = "", String additionalOption = "") {
    if (subProjectName.isEmpty()) {
        sh "gradle :build ${additionalOption}"
    } else {
        sh "gradle :${subProjectName}:build ${additionalOption}"
    }
}

def test(String subProjectName = "", Boolean openapiUsed = false, String additionalOption = "") {
//    def subProjectName = params.project.subProjectName
//    def dockerImageName = params.project.dockerImageName
    def extras = ""

    if (openapiUsed && !subProjectName.isEmpty()) {
        def extraVars = [subProjectName: subProjectName]
        sh "gradle :${subProjectName}:openapi3 ${additionalOption}"

        ansiblePlaybook(
            extraVars: extraVars,
            installation: 'ansible',
            inventory: "/home/ubuntu/ansibles/inventories/dev",
            playbook: "/home/ubuntu/ansibles/ncp_documentation.yml",
            credentialsId: 'LeeJaeDoo',
            extras: "--ssh-common-args='-o StrictHostKeyChecking=no' "
        )
    } else {
        sh "gradle ${subProjectName}:test ${extras} ${params.additionalOption}"
    }
}

def deploy(String playbookName, String  extras = "") {
    ansiblePlaybook(
        installation: 'ansible',
        inventory: "/home/ubuntu/ansibles/inventories/dev",
        playbook: "/home/ubuntu/ansibles/sp-member.yml",
        extras: "--ssh-common-args='-o StrictHostKeyChecking=no' "
    )
}
