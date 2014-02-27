package scanner;

class LinesHandler {
  private int lineInd;
  private int charInd;
  private final String[] lines;

  public LinesHandler(String[] lines) {
    for (int i = 0; i < lines.length; i++)
      lines[i] += " ";
    this.lines = lines;
    reset();
  }

  private void reset() {
    lineInd = 0;
    charInd = 0;
  }

  public String getCurrentChar() {
    return "" + lines[lineInd].charAt(charInd);
  }

  public boolean hasChars() {
    return lineInd < lines.length;
  }

  public void moveForward() {
    charInd++;
    if (noCharsLeftInLine())
      moveToNextLine();
  }

  public void moveBackward() {
    charInd--;
    if (charInd < 0)
      moveToPrevLine();
  }

  void generateLexicalException() {
    moveBackward();
    System.err.println("\nLexical error (line: " + (getLineNo() + 1) + "): \"" + getCurrentChar() + "\" does not begin a valid token.");
    moveForward();
  }

  int getLineNo() {
    return lineInd;
  }

  private void moveToPrevLine() {
    lineInd--;
    if (lineInd < 0)
      reset();
    else
      charInd = lines[lineInd].length() - 1;
  }

  private void moveToNextLine() {
    charInd = 0;
    lineInd++;
  }

  private boolean noCharsLeftInLine() {
    return charInd >= lines[lineInd].length();
  }
  
  String getLineUpToCurrChar() {
	  return lines[lineInd].substring(0, charInd);
  }

  public String getLineInfo() {
    return "(line " + (lineInd + 1) + "): " + getLineUpToCurrChar();
  }

}
