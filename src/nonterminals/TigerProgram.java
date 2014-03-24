package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

public class TigerProgram extends ParserRule 
{
	public TigerProgram(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse()
	{
		lineNumber = scanner.getLineNum();
		matchTerminal("LET");
		matchNonTerminal(new DeclarationSegment(scanner));
		matchTerminal("IN");
		matchNonTerminal(new StatSequence(scanner));
		matchTerminal("END");
	}

	@Override
	public String getLabel() 
	{
		return "<tiger-program>";
	}

	@Override
	public Type getType() 
	{
		return null;
	}
}
