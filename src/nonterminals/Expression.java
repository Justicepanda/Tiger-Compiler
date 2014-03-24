package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class Expression extends ParserRule {

	public Expression(Scanner scanner) {
		super(scanner);
	}
	
	@Override
	public void parse() 
	{
		if(peekTypeMatches("MINUS")) {
      matchTerminal("MINUS");
      matchNonTerminal(new Expression(scanner));
    }
    else {
			matchNonTerminal(new AndOrTerm(scanner));
		}
	}

  @Override
  public String getLabel() {
    return "<expr>";
  }
}
