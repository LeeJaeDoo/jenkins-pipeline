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
  git pull https://LeeJaeDoo:fd3ea2c7b6d80a0282f105a3ea2e2acda53b0012@github.com/LeeJaeDoo/ansibles.git master'''
}
