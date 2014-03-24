package parser;

import compiler.TokenTuple;
import compiler.Compiler;

public class ParserException extends RuntimeException 
{
	ParserException(String s) 
	{
		super(s);
	}
}

class TerminalException extends ParserException 
{
	public TerminalException(TokenTuple actual, TokenTuple expected) 
	{
		super("\"" + actual.getToken() + "\" is not a valid token. Expected \""
				+ expected.getToken() + "\".");
		Compiler.errorOccured();
	}
}

class NonTerminalException extends ParserException 
{
	public NonTerminalException(String expected) 
	{
		super(expected);
		Compiler.errorOccured();
	}
}