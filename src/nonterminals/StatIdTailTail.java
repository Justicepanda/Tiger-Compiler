package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class StatIdTailTail extends ParserRule 
{
	public StatIdTailTail(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() {
		if(peekTypeMatches("LPAREN")) {
			matchTerminal("LPAREN");
			matchNonTerminal(new ExpressionList(scanner));
			matchTerminal("RPAREN");
		}
		else {
			matchNonTerminal(new AndOrTermTail(scanner));
		}
	}

  @Override
  public String getLabel() {
    return "<stat-id-tail-tail>";
  }

}
