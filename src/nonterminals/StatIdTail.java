package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class StatIdTail extends ParserRule 
{
	public StatIdTail(Scanner scanner)
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		if(peekTypeMatches("ID")) {
			matchTerminal("ID");
			matchNonTerminal(new StatIdTailTail(scanner));
		}
		else {
			matchNonTerminal(new Expression(scanner));
		}
	}

  @Override
  public String getLabel() {
    return "<stat-id-tail>";
  }

}
