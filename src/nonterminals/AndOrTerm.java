package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class AndOrTerm extends ParserRule 
{
	public AndOrTerm(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() 
	{
		matchNonTerminal(new IneqTerm(scanner));
		matchNonTerminal(new AndOrTerm2(scanner));
	}

  @Override
  public String getLabel() {
    return "<and-or-term>";
  }
}
