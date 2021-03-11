import com.cloudbees.groovy.cps.NonCPS
import groovy.transform.Field
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper

import java.util.concurrent.TimeUnit

@Field
RunWrapper curBuild = this.currentBuild as RunWrapper

long diffTime(TimeUnit timeUnit) {
  return timeUnit.convert(curBuild.startTimeInMillis - getLastSuccessfulBuildTime(), TimeUnit.MILLISECONDS)
}

@NonCPS
long getLastSuccessfulBuildTime() {
  WorkflowJob job = Jenkins.getInstanceOrNull().getItemByFullName(this.currentBuild.projectName as String) as WorkflowJob

  return job.getLastSuccessfulBuild() == null ? curBuild.startTimeInMillis : job.getLastSuccessfulBuild().time.time
}
