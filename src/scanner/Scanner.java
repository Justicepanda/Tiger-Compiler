package scanner;

import frontend.TokenTuple;

public class Scanner {

  private final TokenDfa dfa;
  private LinesHandler handler;
  private boolean isValid;

  public Scanner(TokenDfa dfa) {
    this.dfa = dfa;
    isValid = true;
  }
  
  public boolean isValid() {
	  return isValid;
  }

  public void scan(String[] toScan) {
    handler = new LinesHandler(toScan);
  }

  public boolean hasMoreTokens() {
    removeSpaces();
    return handler.hasChars();
  }

  private void removeSpaces() {
    while (handler.hasChars() && dfa.isInSpaceState())
      processNextChar();
  }

  /**
   * Finds the next token in the scanned String and returns it. If
   * a lexical error is found, a LexicalException is thrown.
   */
  public TokenTuple getNextToken() {
    TokenTuple token = findToken();
    prepareToFindNextToken();
    if (isComment(token))
      return getNextToken();
    else
      return token;
  }

  private TokenTuple findToken() {
    while (dfa.isNotInAcceptState())
      processNextChar();
    return dfa.getToken();
  }

  private void processNextChar() {
    changeDfaState();
    if (dfa.isNotInAcceptState())
      handleNonAcceptState();
  }

  private void handleNonAcceptState() {
    if (dfa.isInErrorState())
      handleErrorState();
    else if (dfa.isInSpaceState())
      dfa.reset();
  }

  private void handleErrorState() {
    handler.generateLexicalException();
    isValid = false;
    dfa.reset();
  }

  private void prepareToFindNextToken() {
    dfa.reset();
    handler.moveBackward();
  }

  private boolean isComment(TokenTuple token) {
    return token.getType().equals("COMMENT");
  }

  private void changeDfaState() {
    dfa.changeState(handler.getCurrentChar());
    handler.moveForward();
  }

  public String getLineInfo() {
    return handler.getLineInfo();
  }
}
