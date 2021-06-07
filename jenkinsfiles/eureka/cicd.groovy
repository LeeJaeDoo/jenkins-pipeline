package eureka

import org.jenkinsci.plugins.workflow.libs.Library
import sp.enums.Project

@Library('shared') _
/**
 * @author Jaedoo Lee
 */

node {
  try {
    pipelineStage.notifySlack("STARTED", "#0000FF")
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
    pipelineStage.notifySlack("SUCCESS", "#00FF00")
  } catch (e) {
    pipelineStage.notifySlack("FAILED", "#FF0000")
  }
}
