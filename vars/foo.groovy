
/**
 * @author Jaedoo Lee
 */

static def branchName(String stage) {
  if (stage == null) {
    return ""
  } else {
    return branchName(valueOf(stage.toUpperCase()))
  }
}

static def branchName(StageStatus stageStatus) {
  String ret = "master"
  switch (stageStatus) {
    case DEV: ret = "develop"
      break
    default:
      break
  }

  return ret
}
