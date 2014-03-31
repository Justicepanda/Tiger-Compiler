package nonterminals;

import parser.ParserRule;
import symboltable.Type;

class TypeId extends ParserRule {
  private Type type;

  @Override
  public void parse() {
    if (peekTypeMatches("INT"))
      matchInt();
    else if (peekTypeMatches("STRING"))
      matchString();
    else
      matchId();
  }

  private void matchInt() {
    type = Type.INT_TYPE;
    matchTerminal("INT");
  }

  private void matchString() {
    type = Type.STRING_TYPE;
    matchTerminal("STRING");
  }

  private void matchId() {
    String id = matchIdAndGetValue();
    type = getType(id);
  }

  @Override
  public String getLabel() {
    return "<type-id>";
  }

  public Type getType() {
    return type;
  }

  @Override
  public String generateCode() {
    return null;
  }
}
