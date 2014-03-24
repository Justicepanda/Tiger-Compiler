package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class Ineq extends ParserRule 
{
	public Ineq(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() {
		if (peekTypeMatches("NEQ")) {
      matchTerminal("NEQ");
    }
    else if (peekTypeMatches("EQ")) {
      matchTerminal("EQ");
    }
    else if (peekTypeMatches("LESSER")) {
      matchTerminal("LESSER");
    }
    else if (peekTypeMatches("GREATER")) {
      matchTerminal("GREATER");
    }
    else if (peekTypeMatches("LESSEREQ")) {
      matchTerminal("LESSEREQ");
    }
    else {
      matchTerminal("GREATEREQ");
    }
	}

  @Override
  public String getLabel() {
    return "<ineq>";
  }

}
