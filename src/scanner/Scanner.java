package scanner;

import compiler.TokenTuple;

public class Scanner {

  private final TokenDfa dfa;
  private LinesHandler handler;

  public Scanner(TokenDfa dfa) {
    this.dfa = dfa;
  }
  
  public void scan(String[] toScan) {
    handler = new LinesHandler(toScan);
  }

  public boolean hasMoreTokens() {
    return handler.hasChars();
  }

  private void removeSpaces() {
    while (handler.hasChars() && handler.isAtSpaceChar() && dfa.isInSpaceState())
      processNextChar(handler.getCurrentChar());
  }

  /**
   * Finds the next token in the scanned String and returns it. If
   * a lexical error is found, a LexicalException is thrown.
   */
  public TokenTuple getNextToken() {
    return getToken();
  }

  public TokenTuple getToken() {
    TokenTuple token = findToken();
    prepareToFindNextToken();
    if (isComment(token))
      return getToken();
    else
      return token;
  }

  private TokenTuple findToken() {
    while (dfa.isNotInAcceptState())
      processNextChar(handler.getCurrentChar());
    return dfa.getToken();
  }

  private void processNextChar(char c) {
    changeDfaState(c);
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
    dfa.reset();
    throw handler.generateLexicalException();
  }

  private void prepareToFindNextToken() {
    dfa.reset();
    handler.moveBackward();
    removeSpaces();
  }

  private boolean isComment(TokenTuple token) {
    return token.getType().equals("COMMENT");
  }

  private void changeDfaState(char c) {
    dfa.changeState(c);
    handler.moveForward();
  }

  public String getLineInfo() {
    return handler.getLineInfo();
  }

  public static Scanner debug(TokenDfa tokenDfa) {
    return new DebugScanner(tokenDfa);
  }
}

class DebugScanner extends Scanner {

  public DebugScanner(TokenDfa dfa) {
    super(dfa);
  }

  @Override
  public TokenTuple getNextToken() {
    TokenTuple t = super.getNextToken();
    System.out.print(t.getType() + " ");
    return t;
  }
}
