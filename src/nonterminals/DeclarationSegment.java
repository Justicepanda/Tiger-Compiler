package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

class DeclarationSegment extends ParserRule 
{	
	DeclarationSegment(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		matchNonTerminal(new TypeDeclarationList(scanner));
		matchNonTerminal(new VariableDeclarationList(scanner));
		matchNonTerminal(new FunctionDeclarationList(scanner));
	}

	@Override
	public String getLabel()
	{
		return "<declaration-segment>";
	}

	@Override
	public Type getType() 
	{
		return null;
	}
}
