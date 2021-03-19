package member.internal

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
    pipelineStage.clean(Project.MEMBER_FRONT.subProjectName)
  }
  stage ('BUILD') {
    pipelineStage.build(Project.MEMBER_FRONT.subProjectName)
  }
  stage ('TEST') {
    pipelineStage.test(Project.MEMBER_FRONT.subProjectName)
  }
  stage ('DEPLOY') {
    pipelineStage.deploy(Project.MEMBER_FRONT.projectName + "-" + Project.MEMBER_FRONT.subProjectName)
  }
}