package sp.enums

import com.cloudbees.groovy.cps.NonCPS

enum Project {
  /* sp-member */
  MEMBER_FRONT("member", "front", Repository.MEMBER),
  MEMBER_INTERNAL("member", "internal", Repository.MEMBER),

  /* sp-mart */
  MART_FRONT("mart", "front", Repository.MART),
  MART_INTERNAL("mart", "internal", Repository.MART),

  /* sp-gateway */
  GATEWAY("gateway", Repository.GATEWAY),

  /* sp-eureka */
  EUREKA("eureka", Repository.EUREKA),

  /* sp-auth */
  AUTH_INTERNAL("auth", "internal", Repository.AUTH)

  private String projectName
  private String subProjectName
  private Repository repositoryName

  Project(String projectName, String subProjectName, Repository repositoryName) {
    this.projectName = projectName
    this.subProjectName = subProjectName
    this.repositoryName = repositoryName
  }

  Project(String projectName, Repository repositoryName) {
    this.projectName = projectName
    this.repositoryName = repositoryName
  }

  @NonCPS
  String getProjectName() {
    return projectName
  }

  @NonCPS
  String getSubProjectName() {
    return subProjectName
  }

  @NonCPS
  String getRepositoryName() {
    return repositoryName.getRepositoryName()
  }
}
