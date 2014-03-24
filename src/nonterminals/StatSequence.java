package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

class StatSequence extends ParserRule 
{
	StatSequence(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("RETURN") || peekTypeMatches("ID")
				|| peekTypeMatches("IF") || peekTypeMatches("WHILE")
				|| peekTypeMatches("FOR") || peekTypeMatches("BREAK")) 
		{
			matchNonTerminal(new Stat(scanner));
			matchNonTerminal(new StatSequence(scanner));
		}
	}

	@Override
	public String getLabel() 
	{
		return "<stat-seq>";
	}

	@Override
	public Type getType() 
	{
		return null;
	}
}
