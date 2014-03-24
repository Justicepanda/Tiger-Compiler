package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class StatTail extends ParserRule {

  private StatSequence statSequence;

  @Override
  public void parse() {
    if (peekTypeMatches("ENDIF")) {
      matchTerminal("ENDIF");
      matchTerminal("SEMI");
    } else {
      statSequence = new StatSequence();
      matchTerminal("ELSE");
      matchNonTerminal(statSequence);
      matchTerminal("ENDIF");
      matchTerminal("SEMI");
    }
  }

  @Override
  public String getLabel() {
    return "<stat-tail>";
  }

  @Override
  public Type getType() {
    return null;
  }

}
