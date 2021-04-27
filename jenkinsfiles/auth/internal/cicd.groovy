package auth.internal

import org.jenkinsci.plugins.workflow.libs.Library
import sp.enums.Project

@Library('shared') _
/**
 * @author Jaedoo Lee
 */

node {
  stage ('CHECKOUT') {
    pipelineStage.gitCheckout(Project.AUTH_INTERNAL.getRepositoryName())
  }
  stage ('CLEAN') {
    pipelineStage.clean(Project.AUTH_INTERNAL.subProjectName)
  }
  stage ('BUILD & TEST') {
    pipelineStage.build(Project.AUTH_INTERNAL.subProjectName)
  }
//  stage ('TEST') {
//    pipelineStage.test(Project.MEMBER_INTERNAL.subProjectName)
//  }
  stage ('DEPLOY') {
    pipelineStage.deploy("${Project.AUTH_INTERNAL.projectName}-${Project.AUTH_INTERNAL.subProjectName}")
  }
}
