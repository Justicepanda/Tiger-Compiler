package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class AddTermTail extends ParserRule 
{
	public AddTermTail(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() 
	{
		matchNonTerminal(new MultTermTail(scanner));
		matchNonTerminal(new AddTerm2(scanner));
  }

  @Override
  public String getLabel() {
    return "<add-term-tail>";
  }
}
