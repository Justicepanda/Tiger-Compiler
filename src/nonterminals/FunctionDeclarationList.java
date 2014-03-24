package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

class FunctionDeclarationList extends ParserRule 
{	
	FunctionDeclarationList(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("FUNC")) 
		{
			matchNonTerminal(new FunctionDeclaration(scanner));
			matchNonTerminal(new FunctionDeclarationList(scanner));
		}
	}

	@Override
	public String getLabel() 
	{
		return "<funct-declaration-list>";
	}

	@Override
	public Type getType() 
	{
		return null;
	}
}
