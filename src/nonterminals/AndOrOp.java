package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class AndOrOp extends ParserRule 
{
	public AndOrOp(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() 
	{
    if (peekTypeMatches("OR"))
      matchTerminal("OR");
    else
      matchTerminal("AND");
  }

  @Override
  public String getLabel() {
    return "<and-or-op>";
  }
}
