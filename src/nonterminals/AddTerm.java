package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class AddTerm extends ParserRule {
	public AddTerm(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() 
	{
		matchNonTerminal(new MultTerm(scanner));
		matchNonTerminal(new AddTerm2(scanner));
	}

  @Override
  public String getLabel() {
    return "<add-term>";
  }

}
