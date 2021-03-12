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
  git pull https://LeeJaeDoo:232317c8e8be656918b8bc424f05e9a419b1cf62@github.com/LeeJaeDoo/ansibles.git master'''
}
