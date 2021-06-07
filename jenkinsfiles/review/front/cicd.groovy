package review.front

import org.jenkinsci.plugins.workflow.libs.Library
import sp.enums.Project

@Library('shared') _
/**
 * @author Jaedoo Lee
 */

node {
  try {
    notifySlack("STARTED", "#0000FF")
    stage ('CHECKOUT') {
      pipelineStage.gitCheckout(Project.REVIEW_FRONT.getRepositoryName())
    }
    stage ('CLEAN') {
      pipelineStage.clean(Project.REVIEW_FRONT.subProjectName)
    }
    stage ('BUILD') {
      pipelineStage.build(Project.REVIEW_FRONT.subProjectName)
    }
    stage ('TEST') {
      pipelineStage.test(Project.REVIEW_FRONT.subProjectName, true)
    }
    stage ('DEPLOY') {
      pipelineStage.deploy("${Project.REVIEW_FRONT.projectName}-${Project.REVIEW_FRONT.subProjectName}")
    }
    notifySlack("SUCCESS", "#00FF00")
  } catch (e) {
    notifySlack("FAILED", "#FF0000")
  }
}
