package parser;

import compiler.Compiler;

public class SemanticTypeException extends RuntimeException 
{
	public SemanticTypeException(int lineNum)
	{
		super("Semantic error on line " + lineNum + "!");
		Compiler.errorOccured();
	}
}
