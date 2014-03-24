package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class Factor extends ParserRule 
{
	public Factor(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() {
    if (peekTypeMatches("LPAREN")) {
      matchTerminal("LPAREN");
      matchNonTerminal(new Expression(scanner));
      matchTerminal("RPAREN");
    }
    else if (peekTypeMatches("INTLIT") ||
            peekTypeMatches("STRLIT") ||
            peekTypeMatches("NIL")) {
      matchNonTerminal(new Constant(scanner));
    }
    else {
      matchTerminal("ID");
      matchNonTerminal(new LValue(scanner));
    }
	}

  @Override
  public String getLabel() {
    return "<factor>";
  }

}
