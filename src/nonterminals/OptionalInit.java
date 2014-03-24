package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

public class OptionalInit extends ParserRule 
{
	private Constant constant;
	private Type type;

	public OptionalInit(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("ASSIGN")) 
		{
			matchTerminal("ASSIGN");
			matchNonTerminal(constant = new Constant(scanner));
			type = constant.getType();
		}
		else
		{
			type = null;
		}
	}

	@Override
	public String getLabel()
	{
		return "<optional-init>";
	}

	public String getValue() 
	{
		return constant.getValue();
	}

	@Override
	public Type getType() 
	{
		return type;
	}
}
