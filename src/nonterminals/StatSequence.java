package nonterminals;

import parser.ParserRule;
import symboltable.Type;

class StatSequence extends ParserRule {
  private Stat stat;
  private StatSequence statSequence;

  @Override
  public void parse() {
    if (isStatement()) {
      stat = new Stat();
      statSequence = new StatSequence();
      matchNonTerminal(stat);
      matchNonTerminal(statSequence);
    }
  }

  private boolean isStatement() {
    return peekTypeMatches("RETURN") || peekTypeMatches("ID")
            || peekTypeMatches("IF") || peekTypeMatches("WHILE")
            || peekTypeMatches("FOR") || peekTypeMatches("BREAK");
  }

  @Override
  public String getLabel() {
    return "<stat-seq>";
  }

  @Override
  public Type getType() {
    return null;
  }
}
