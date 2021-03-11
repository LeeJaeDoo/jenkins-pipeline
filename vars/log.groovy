import groovy.transform.Field

@Field int ONE_LINE_BYTES = 30
@Field def DELIMITER = "="
@Field def NEW_LINE = "\n"

def err(message) {
  echo "\n[ERROR] : ${message}"
}

def withStepNoEnd(Map config, Closure body) {
  echo "${makeStartLogText(config["stageName"] as String)}"
  body()
}

def makeStartLogText(String stageName) {
  String EMPTY = " "

  int messageLength = stageName.length()
  boolean isEvenNumber = messageLength % 2
  int tmp = isEvenNumber ? messageLength : messageLength + 1

  int left = (int) ((this.ONE_LINE_BYTES / 2 - tmp / 2) - 1)

  StringBuffer sb = new StringBuffer("$NEW_LINE$NEW_LINE")
  appendLog(left, sb)
  sb.append("${EMPTY}${stageName}${EMPTY}")
  appendLog(this.ONE_LINE_BYTES - sb.toString().length() + (isEvenNumber ? 5 : 4), sb)

  return sb.toString()
}

private void appendLog(int tmp, StringBuffer sb) {
  for (int i = 0; i < tmp; i++) {
    sb.append(DELIMITER)
  }
}
