package sp.enums

import com.cloudbees.groovy.cps.NonCPS

import java.util.stream.Collectors

/**
 * @author Jaedoo Lee
 */
enum BuildStage {
  CHECKOUT,
  CLEAN,
  BUILD,
  TEST,
  DEPLOY

  @NonCPS
  static String concatWith(BuildStage... status) {
    return status ? Arrays.stream(status).map{ it.toString() }.collect(Collectors.joining(",")) : ""
  }
}
