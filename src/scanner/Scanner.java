package scanner;

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

  public void scan(String toScan) {
    scan(new String[]{toScan});
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
    
    if(token.getType().equals("COMMENT"))
    	return getNextToken();
    
    return token;
  }

  public TokenTuple peekAtNextToken() {
    TokenTuple token = peekToken();
    dfa.reset();
    
    if(token.getType().equals("COMMENT"))
    	return peekAtNextToken();
    
    return token;
  }

  private TokenTuple peekToken() {
    int countToMoveBack = 0;
    while (!dfa.isInAcceptState()) {
      countToMoveBack++;
      processNextChar();
    }
    for (int i = 0; i < countToMoveBack; i++) {
      handler.moveBackward();
    }
    
    return dfa.getToken();
  }

  private TokenTuple findToken() {
    while (!dfa.isInAcceptState())
      processNextChar();
    return dfa.getToken();
  }

  private void processNextChar() {
    changeDfaState();
    if (!dfa.isInAcceptState())
      handleNonAcceptState();
  }

  private void prepareToFindNextToken() {
    dfa.reset();
    handler.moveBackward();
  }

  private void handleNonAcceptState() {
    if (dfa.isInErrorState())
    {
    	handler.moveBackward();
    	System.err.println("\nLexical error (line: " + (handler.getLineNo() + 1) + "): \"" + handler.getCurrentChar() + "\" does not begin a valid token.");
    	handler.moveForward();
    	isValid = false;
    	
    	dfa.reset();
    }
    else if (dfa.isInSpaceState())
    {
    	dfa.reset();
    }
  }

  private void changeDfaState() {
    dfa.changeState(handler.getCurrentChar());
    handler.moveForward();
  }
  
  public LinesHandler getLineHandler()
  {
	  return handler;
  }
}
