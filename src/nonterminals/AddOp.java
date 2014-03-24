package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

public class AddOp extends ParserRule
{
	public AddOp(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("PLUS"))
			matchTerminal("PLUS");
		else
			matchTerminal("MINUS");
	}

	@Override
	public String getLabel() 
	{
		return "<add-op>";
	}

	@Override
	public Type getType() 
	{
		return null;
	}
}
