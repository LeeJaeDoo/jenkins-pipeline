package eureka

import org.jenkinsci.plugins.workflow.libs.Library
import sp.enums.Project

@Library('shared') _
/**
 * @author Jaedoo Lee
 */

node {
  stage ('CHECKOUT') {
    pipelineStage.gitCheckout()
  }
  stage ('CLEAN') {
    pipelineStage.clean()
  }
  stage ('BUILD') {
    pipelineStage.build()
  }
  stage ('TEST') {
    pipelineStage.test()
  }
  stage ('DEPLOY') {
    pipelineStage.deploy(Project.EUREKA.projectName)
  }
}
