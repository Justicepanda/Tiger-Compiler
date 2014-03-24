package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class MultTerm2 extends ParserRule 
{
	public MultTerm2(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() 
	{
    if (peekTypeMatches("MULT") || peekTypeMatches("DIV")) {
      matchNonTerminal(new MultOp(scanner));
      matchNonTerminal(new Factor(scanner));
      matchNonTerminal(new MultTerm2(scanner));
    }
	}

  @Override
  public String getLabel() {
    return "<mult-term2>";
  }

}
