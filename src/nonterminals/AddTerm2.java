package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class AddTerm2 extends ParserRule 
{
	public AddTerm2(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() 
	{
    if (peekTypeMatches("PLUS") || peekTypeMatches("MINUS")) {
      matchNonTerminal(new AddOp(scanner));
      matchNonTerminal(new MultTerm(scanner));
      matchNonTerminal(new AddTerm2(scanner));
    }
	}

  @Override
  public String getLabel() {
    return "<add-term2>";
  }
}
