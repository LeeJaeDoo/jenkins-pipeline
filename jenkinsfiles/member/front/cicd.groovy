package member.front

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
      pipelineStage.gitCheckout(Project.MEMBER_FRONT.getRepositoryName())
    }
    stage ('CLEAN') {
      pipelineStage.clean(Project.MEMBER_FRONT.subProjectName)
    }
    stage ('BUILD') {
      pipelineStage.build(Project.MEMBER_FRONT.subProjectName)
    }
    stage ('TEST') {
      pipelineStage.test(Project.MEMBER_FRONT.subProjectName, true)
    }
    stage ('DEPLOY') {
      pipelineStage.deploy("${Project.MEMBER_FRONT.projectName}-${Project.MEMBER_FRONT.subProjectName}")
    }
    pipelineStage.notifySlack("SUCCESS", "#00FF00")
  } catch (e) {
    pipelineStage.notifySlack("FAILED", "#FF0000")
  }
}
