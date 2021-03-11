import com.cloudbees.groovy.cps.NonCPS
import com.ncp.enums.Credential
import com.ncp.params.JobParams
import groovy.transform.Field
import hudson.model.Cause
import hudson.triggers.TimerTrigger
import org.jenkinsci.plugins.workflow.cps.replay.ReplayCause
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper

import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

import static com.ncp.enums.LogLevel.*

def err(message) {
  echo "\n[ERROR] : ${message}"
}

def withStepNoEnd(Map config, Closure body) {
  echo "${makeStartLogText(config["stageName"] as String)}"
  body()
}
