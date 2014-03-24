package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class LValue extends ParserRule {
	public LValue(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() {
		if (peekTypeMatches("LBRACK")) {
      matchTerminal("LBRACK");
      matchNonTerminal(new Expression(scanner));
      matchTerminal("RBRACK");
      matchNonTerminal(new LValue(scanner));
    }
	}

  @Override
  public String getLabel() {
    return "<lvalue>";
  }

}
