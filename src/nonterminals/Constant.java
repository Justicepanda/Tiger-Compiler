package nonterminals;

import parser.ParserRule;
import symboltable.Type;
import scanner.Scanner;

public class Constant extends ParserRule 
{
	private String value;
	private Type type;

	public Constant(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		value = peekTokenValue();
		if (peekTypeMatches("INTLIT"))
		{
			type = new Type("int", "int");
			matchTerminal("INTLIT");
		}
		else if (peekTypeMatches("STRLIT"))
		{	
			type = new Type("string", "string");
			matchTerminal("STRLIT");
		}
		else
		{
			type = new Type("nil", "nil");
			matchTerminal("NIL");
		}
	}

	@Override
	public String getLabel() 
	{
		return "<const>";
	}

	public String getValue() 
	{
		if (value == null)
			return "nil";
		else
			return value;
	}

	@Override
	public Type getType() 
	{
		return type;
	}
}
