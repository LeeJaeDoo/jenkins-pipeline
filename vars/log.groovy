def err(message) {
  echo "\n[ERROR] : ${message}"
}

def withStepNoEnd(Map config, Closure body) {
  echo "${makeStartLogText(config["stageName"] as String)}"
  body()
}
