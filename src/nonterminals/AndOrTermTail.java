package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class AndOrTermTail extends ParserRule 
{
	public AndOrTermTail(Scanner scanner)
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		matchNonTerminal(new IneqTermTail(scanner));
		matchNonTerminal(new AndOrTerm2(scanner));
	}

  @Override
  public String getLabel() {
    return "<and-or-term-tail>";
  }

}
