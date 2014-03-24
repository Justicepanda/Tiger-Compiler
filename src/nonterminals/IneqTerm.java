package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class IneqTerm extends ParserRule 
{
	public IneqTerm(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() {
		matchNonTerminal(new AddTerm(scanner));
		matchNonTerminal(new IneqTerm2(scanner));
	}

  @Override
  public String getLabel() {
    return "<ineq-term>";
  }
}
