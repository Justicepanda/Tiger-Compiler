package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class MultTermTail extends ParserRule 
{
	public MultTermTail(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() {
		matchNonTerminal(new LValue(scanner));
		matchNonTerminal(new MultTerm2(scanner));
	}

  @Override
  public String getLabel() {
    return "<mult-term-tail>";
  }
}
