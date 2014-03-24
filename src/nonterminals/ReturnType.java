package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

public class ReturnType extends ParserRule 
{

	private TypeId typeId;

	public ReturnType(Scanner scanner) 
	{
		super(scanner);
		typeId = new TypeId(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("COLON")) 
		{
			matchTerminal("COLON");
			matchNonTerminal(typeId);
		}
	}

	@Override
	public String getLabel()
	{
		return "<ret-type>";
	}

	public Type getType() 
	{
		return typeId.getType();
	}
}
