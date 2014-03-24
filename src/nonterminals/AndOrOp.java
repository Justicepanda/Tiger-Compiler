package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

public class AndOrOp extends ParserRule 
{	
	public AndOrOp(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("OR"))
			matchTerminal("OR");
		else
			matchTerminal("AND");
	}

	@Override
	public String getLabel() 
	{
		return "<and-or-op>";
	}

	@Override
	public Type getType() 
	{
		return null;
	}
}
