package gateway

import org.jenkinsci.plugins.workflow.libs.Library
import sp.enums.Project

@Library('shared') _
/**
 * @author Jaedoo Lee
 */

node {
  stage ('CHECKOUT') {
    pipelineStage.gitCheckout(Project.GATEWAY.getRepositoryName())
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
    pipelineStage.deploy(Project.GATEWAY.projectName)
  }
}
