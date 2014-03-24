package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

class VariableDeclarationList extends ParserRule 
{
	VariableDeclarationList(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("VAR")) 
		{
			matchNonTerminal(new VariableDeclaration(scanner));
			matchNonTerminal(new VariableDeclarationList(scanner));
		}
	}

	@Override
	public String getLabel() 
	{
		return "<var-declaration-list>";
	}

	@Override
	public Type getType() 
	{
		return null;
	}
}
