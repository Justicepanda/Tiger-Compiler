package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class MultTerm extends ParserRule 
{
	public MultTerm(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() {
		matchNonTerminal(new Factor(scanner));
		matchNonTerminal(new MultTerm2(scanner));
	}

  @Override
  public String getLabel() {
    return "<mult-term>";
  }
}
