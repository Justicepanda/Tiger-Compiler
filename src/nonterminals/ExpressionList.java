package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class ExpressionList extends ParserRule {

	public ExpressionList(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() 
	{
	  if (peekTypeMatches("ID") ||
            peekTypeMatches("LPAREN") ||
            peekTypeMatches("INTLIT") ||
            peekTypeMatches("STRLIT") ||
            peekTypeMatches("NIL")) {
      matchNonTerminal(new Expression(scanner));
      matchNonTerminal(new ExpressionListTail(scanner));
    }
	}

  @Override
  public String getLabel() {
    return "<expr-list>";
  }

}
