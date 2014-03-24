package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

class TypeId extends ParserRule
{
	private Type type;

	TypeId(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("INT"))
		{
			type = new symboltable.Type("int", "int");
			matchTerminal("INT");
		} 
		else if (peekTypeMatches("STRING")) 
		{
			type = new Type("string", "string");
			matchTerminal("STRING");
		} 
		else 
		{
			type = symbolTable.getType(peekTokenValue());
			matchTerminal("ID");
		}
	}

	@Override
	public String getLabel() 
	{
		return "<type-id>";
	}

	public Type getType() 
	{
		return type;
	}
}
