package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class StatTail extends ParserRule {

  private StatSequence statSequence;

  @Override
  public void parse() {
    if (peekTypeMatches("ENDIF"))
      matchEndif();
    else
      matchElse();
  }

  private void matchElse() {
    statSequence = new StatSequence();
    matchTerminal("ELSE");
    matchNonTerminal(statSequence);
    matchEndif();
  }

  private void matchEndif() {
    matchTerminal("ENDIF");
    matchTerminal("SEMI");
  }

  @Override
  public String getLabel() {
    return "<stat-tail>";
  }

  @Override
  public Type getType() {
    return null;
  }

  @Override
  public String generateCode() {
    return null;
  }

}
