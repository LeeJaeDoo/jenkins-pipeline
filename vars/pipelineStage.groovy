def gitCheckout(String repositoryName) {
  checkout([$class                           : 'GitSCM',
            branches                         : [[name: "*/master"]],
            doGenerateSubmoduleConfigurations: false,
            extensions                       : [[$class: 'WipeWorkspace'], [$class: 'LocalBranch', localBranch: '**']],
            userRemoteConfigs                : [[credentialsId: 'LeeJaeDoo', url: "https://LeeJaeDoo:262283b0c7139420fc2eb4f2d2a6b0e8d6fc19d4@github.com/LeeJaeDoo/${repositoryName}"]]])
}

def clean(String subProjectName = "", String additionalOption = "") {
  if (subProjectName.isEmpty()) {
    sh script: "-c ./gradlew clean -q ${additionalOption}"
  } else {
    sh script: "-c ./gradlew :${subProjectName}:clean -q ${additionalOption}"
  }
}

def build(String subProjectName = "", String additionalOption = "") {
  if (subProjectName.isEmpty()) {
    sh script: "-c ./gradlew :build ${additionalOption}"
  } else {
    sh script: "-c ./gradlew :${subProjectName}:build ${additionalOption}"
  }
}

def test(String subProjectName = "", Boolean openapiUsed = false, String additionalOption = "") {
  def extras = ""

  if (openapiUsed && !subProjectName.isEmpty()) {
    def extraVars = [subProjectName: subProjectName]
    sh script: "-c ./gradlew :${subProjectName}:openapi3 ${additionalOption}"

    ansiblePlaybook(
        extraVars: extraVars,
        inventory: "/home/ubuntu/ansibles/inventories/hosts/inventory",
        playbook: "/home/ubuntu/ansibles/sp-documentation.yml",
        credentialsId: 'LeeJaeDoo',
        extras: "--ssh-common-args='-o StrictHostKeyChecking=no' "
    )
  } else {
    sh script: "-c ./gradlew ${subProjectName}:test ${extras}"
  }
}

def deploy(String playbookName = "", String  extras = "") {
  ansiblePlaybook(
      inventory: "/home/ubuntu/ansibles/inventories/hosts/inventory",
      playbook: "/home/ubuntu/ansibles/${playbookName}.yml",
      extras: "--ssh-common-args='-o StrictHostKeyChecking=no' "
  )
}
