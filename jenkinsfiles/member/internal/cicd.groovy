package member.internal

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
      pipelineStage.gitCheckout(Project.MEMBER_INTERNAL.getRepositoryName())
    }
    stage ('CLEAN') {
      pipelineStage.clean(Project.MEMBER_INTERNAL.subProjectName)
    }
    stage ('BUILD') {
      pipelineStage.build(Project.MEMBER_INTERNAL.subProjectName)
    }
    stage ('TEST') {
      pipelineStage.test(Project.MEMBER_INTERNAL.subProjectName, true)
    }
    stage ('DEPLOY') {
      pipelineStage.deploy("${Project.MEMBER_INTERNAL.projectName}-${Project.MEMBER_INTERNAL.subProjectName}")
    }
    notifySlack("SUCCESS", "#00FF00")
  } catch (e) {
    notifySlack("FAILED", "#FF0000")
  }
}
