package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class IneqTerm2 extends ParserRule
{
	public IneqTerm2(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() {
    if (peekTypeMatches("NEQ") ||
            peekTypeMatches("EQ") ||
            peekTypeMatches("LESSER") ||
            peekTypeMatches("GREATER") ||
            peekTypeMatches("LESSEREQ") ||
            peekTypeMatches("GREATEREQ")) {
      matchNonTerminal(new Ineq(scanner));
      matchNonTerminal(new AddTerm(scanner));
      matchNonTerminal(new IneqTerm2(scanner));
    }
	}

  @Override
  public String getLabel() {
    return "<ineq-term-2>";
  }
}
