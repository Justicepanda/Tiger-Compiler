package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class MultOp extends ParserRule 
{
	public MultOp(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() {
		if (peekTypeMatches("MULT")) {
      matchTerminal("MULT");
    }
    else {
      matchTerminal("DIV");
    }
	}

  @Override
  public String getLabel() {
    return "<mult-op>";
  }

}
