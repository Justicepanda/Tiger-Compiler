package parser;

import compiler.TokenTuple;

public class Error 
{
	private String errorString;
	
	public Error(String error, int line, TokenTuple token)
	{
		errorString = error + " at line " + line + " at the token " + token;
	}
	
	public String getError()
	{
		return errorString;
	}
}
