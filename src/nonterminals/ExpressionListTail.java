package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class ExpressionListTail extends ParserRule
{
	public ExpressionListTail(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() 
	{
    if (peekTypeMatches("COMMA")) {
      matchTerminal("COMMA");
      matchNonTerminal(new Expression(scanner));
      matchNonTerminal(new ExpressionListTail(scanner));
    }
	}

  @Override
  public String getLabel() {
    return "<expr-list-tail>";
  }
}
