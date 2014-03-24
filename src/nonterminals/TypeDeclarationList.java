package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

class TypeDeclarationList extends ParserRule 
{
	TypeDeclarationList(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse()
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("TYPE"))
		{
			matchNonTerminal(new TypeDeclaration(scanner));
			matchNonTerminal(new TypeDeclarationList(scanner));
		}
	}

	@Override
	public String getLabel()
	{
		return "<type-declaration-list>";
	}

	@Override
	public Type getType() 
	{
		return null;
	}
}
