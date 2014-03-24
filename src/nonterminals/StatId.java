package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class StatId extends ParserRule {

	public StatId(Scanner scanner)
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
			matchNonTerminal(new LValue(scanner));
			matchTerminal("ASSIGN");
			matchNonTerminal(new StatIdTail(scanner));
		}
	}

  @Override
  public String getLabel() {
    return "<stat-id>";
  }
}
