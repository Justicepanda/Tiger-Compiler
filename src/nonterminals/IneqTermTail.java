package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class IneqTermTail extends ParserRule
{
	public IneqTermTail(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() {
		matchNonTerminal(new AddTermTail(scanner));
		matchNonTerminal(new IneqTerm2(scanner));
	}

  @Override
  public String getLabel() {
    return "<ineq-term-tail>";
  }

}
