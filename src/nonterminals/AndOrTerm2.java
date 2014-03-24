package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class AndOrTerm2 extends ParserRule
{
	public AndOrTerm2(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() 
	{
    if (peekTypeMatches("AND") || peekTypeMatches("OR")) {
      matchNonTerminal(new AndOrOp(scanner));
      matchNonTerminal(new IneqTerm(scanner));
      matchNonTerminal(new AndOrTerm2(scanner));
    }
	}

  @Override
  public String getLabel() {
    return "<and-or-term2>";
  }
}
