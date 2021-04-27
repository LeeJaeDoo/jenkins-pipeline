package sp.enums

import com.cloudbees.groovy.cps.NonCPS

/**
 * @author Jaedoo Lee
 */
enum Repository {
  MEMBER("sp-member"),
  MART("sp-mart"),
  AUTH("sp-auth"),
  EUREKA("sp-eureka"),
  GATEWAY("sp-gateway")

  private String repositoryName

  Repository(String repositoryName) {
    this.repositoryName = repositoryName
  }

  @NonCPS
  String getRepositoryName() {
    return repositoryName
  }
}
