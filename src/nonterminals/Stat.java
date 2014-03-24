package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class Stat extends ParserRule {

	public Stat(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() 
	{
		if(peekTypeMatches("RETURN")) {
			matchTerminal("RETURN");
			matchNonTerminal(new Expression(scanner));
			matchTerminal("SEMI");
		}
		else if(peekTypeMatches("ID")) {
			matchTerminal("ID");
			matchNonTerminal(new StatId(scanner));
      matchTerminal("SEMI");
    }
		else if(peekTypeMatches("IF")) {
			matchTerminal("IF");
			matchNonTerminal(new Expression(scanner));
			matchTerminal("THEN");
			matchNonTerminal(new StatSequence(scanner));
			matchNonTerminal(new StatTail(scanner));
		}
		else if(peekTypeMatches("WHILE")) {
			matchTerminal("WHILE");
			matchNonTerminal(new Expression(scanner));
			matchTerminal("DO");
			matchNonTerminal(new StatSequence(scanner));
			matchTerminal("ENDDO");
      matchTerminal("SEMI");
    }
		else if(peekTypeMatches("FOR")) {
			matchTerminal("FOR");
			matchTerminal("ID");
			matchTerminal("ASSIGN");
			matchNonTerminal(new Expression(scanner));
			matchTerminal("TO");
			matchNonTerminal(new Expression(scanner));
			matchTerminal("DO");
			matchNonTerminal(new StatSequence(scanner));
			matchTerminal("ENDDO");
      matchTerminal("SEMI");
    }
		else {
			matchTerminal("BREAK");
			matchTerminal("SEMI");
		}
	}

  @Override
  public String getLabel() {
    return "<stat>";
  }

}
