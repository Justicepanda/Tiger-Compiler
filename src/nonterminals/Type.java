package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Array;

class Type extends ParserRule 
{
	private TypeId typeId;

	Type(Scanner scanner) 
	{
		super(scanner);
		typeId = new TypeId(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("ARRAY")) 
		{
			matchTerminal("ARRAY");
			matchTerminal("LBRACK");
			matchTerminal("INTLIT");
			matchTerminal("RBRACK");
			matchTerminal("OF");
			matchNonTerminal(typeId);
		} 
		else
			matchNonTerminal(typeId);
	}

	@Override
	public String getLabel() 
	{
		return "<type>";
	}

	symboltable.Type getChildType() 
	{
		return typeId.getType();
	}

	@Override
	public symboltable.Type getType()
	{
		return typeId.getType();
	}
}
