package sp.enums

import com.cloudbees.groovy.cps.NonCPS

enum Project {
  /* sp-member */
  MEMBER_FRONT("member", "front"),
  MEMBER_INTERNAL("member", "internal"),

  /* sp-mart */
  MART_FRONT("mart", "front"),
  MART_INTERNAL("mart", "internal")

  Project(String projectName, String subProjectName) {
    this.projectName = projectName
    this.subProjectName = subProjectName
  }

  @NonCPS
  String getProjectName() {
    return projectName
  }

  @NonCPS
  String getSubProjectName() {
    return subProjectName
  }
}
