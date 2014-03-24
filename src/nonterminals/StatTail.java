package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class StatTail extends ParserRule {

	public StatTail(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() 
	{
    if(peekTypeMatches("ENDIF")) {
			matchTerminal("ENDIF");
			matchTerminal("SEMI");
		}
		else {
			matchTerminal("ELSE");
			matchNonTerminal(new StatSequence(scanner));
			matchTerminal("ENDIF");
			matchTerminal("SEMI");
		}
	}

  @Override
  public String getLabel() {
    return "<stat-tail>";
  }

}
