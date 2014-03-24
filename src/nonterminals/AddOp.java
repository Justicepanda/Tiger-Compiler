package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class AddOp extends ParserRule {
	public AddOp(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() 
	{
    if (peekTypeMatches("PLUS"))
      matchTerminal("PLUS");
    else
      matchTerminal("MINUS");
	}

  @Override
  public String getLabel() {
    return "<add-op>";
  }

}
