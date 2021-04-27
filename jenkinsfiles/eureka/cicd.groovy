package eureka

import org.jenkinsci.plugins.workflow.libs.Library
import sp.enums.Project

@Library('shared') _
/**
 * @author Jaedoo Lee
 */

node {
  stage ('CHECKOUT') {
    pipelineStage.gitCheckout(Project.EUREKA.getRepositoryName())
  }
  stage ('CLEAN') {
    pipelineStage.clean()
  }
  stage ('BUILD & TEST') {
    pipelineStage.build()
  }
//  stage ('TEST') {
//    pipelineStage.test()
//  }
  stage ('DEPLOY') {
    pipelineStage.deploy(Project.EUREKA.projectName)
  }
}
