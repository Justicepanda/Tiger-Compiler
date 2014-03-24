package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class ReturnType extends ParserRule {
  private TypeId typeId;

  @Override
  public void parse() {
    if (peekTypeMatches("COLON")) {
      typeId = new TypeId();
      matchTerminal("COLON");
      matchNonTerminal(typeId);
    }
  }

  @Override
  public String getLabel() {
    return "<ret-type>";
  }

  public Type getType() {
    if (typeId == null)
      return null;
    return typeId.getType();
  }
}
