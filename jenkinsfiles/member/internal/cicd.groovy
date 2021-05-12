package member.internal

import org.jenkinsci.plugins.workflow.libs.Library
import sp.enums.Project

@Library('shared') _
/**
 * @author Jaedoo Lee
 */

node {
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
}
