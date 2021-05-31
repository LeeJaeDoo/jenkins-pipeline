import org.jenkinsci.plugins.workflow.libs.Library

@Library('shared') _
/**
 * @author Jaedoo Lee1
 */

node {
  stage("GIT PULL") {
    gitPull()
  }
}

def gitPull() {
  sh label: '', script: '''cd /home/ubuntu/ansibles
  git pull https://LeeJaeDoo:262283b0c7139420fc2eb4f2d2a6b0e8d6fc19d4@github.com/it-chip/ansibles.git master'''
}
