package scanner;

import compiler.TokenTuple;
import compiler.Compiler;

public class Scanner 
{
	private final TokenDfa dfa;
	private LinesHandler handler;
	private TokenTuple stored;

  public Scanner() {
    this.dfa = ((TokenDfa) new TokenDfaBuilder().buildFrom("TokenDFA.csv"));
  }

	public void reset() {
		dfa.reset();
	}

	public void scan(String[] toScan) 
	{
		handler = new LinesHandler(toScan);
	}

	private void removeSpaces() 
	{
		while (handler.hasChars() && handler.isAtSpaceChar()
				&& dfa.isInSpaceState())
			processNextChar(handler.getCurrentChar());
	}

	/**
	 * Finds the next token in the scanned String and returns it. If a lexical
	 * error is found, a LexicalException is thrown.
	 */
	public TokenTuple popToken() 
	{
		if (stored == null)
			return getToken();
		else 
		{
			TokenTuple temp = stored;
			stored = null;
			return temp;
		}
	}

	public TokenTuple peekToken() 
	{
		if (stored == null)
			stored = getToken();
		return stored;
	}

	private TokenTuple getToken() 
	{
		TokenTuple token = findToken();
		prepareToFindNextToken();
		if (isComment(token))
			return getToken();
		else
			return token;
	}

	private TokenTuple findToken() 
	{
		while (dfa.isNotInAcceptState())
			processNextChar(handler.getCurrentChar());
		return dfa.getToken();
	}

	private void processNextChar(char c) 
	{
		changeDfaState(c);
		if (dfa.isNotInAcceptState())
			handleNonAcceptState();
	}

	private void handleNonAcceptState() 
	{
		if (dfa.isInErrorState())
			handleErrorState();
		else if (dfa.isInSpaceState())
			dfa.reset();
	}

	private void handleErrorState() 
	{
		dfa.reset();
		handler.printLexicalException();
		Compiler.errorOccured();
		popToken();
	}

	private void prepareToFindNextToken() 
	{
		dfa.reset();
		handler.moveBackward();
		removeSpaces();
	}

	private boolean isComment(TokenTuple token) 
	{
		return token.getType().equals("COMMENT");
	}

	private void changeDfaState(char c) 
	{
		dfa.changeState(c);
		handler.moveForward();
	}

	public int getLineNum() 
	{
		return handler.getCurrentLine()+1;
	}
}
