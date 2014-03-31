package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class ReturnType extends ParserRule {
  private TypeId typeId;

  @Override
  public void parse() {
    if (peekTypeMatches("COLON"))
      matchReturnType();
  }

  private void matchReturnType() {
    typeId = new TypeId();
    matchTerminal("COLON");
    matchNonTerminal(typeId);
  }

  @Override
  public String getLabel() {
    return "<ret-type>";
  }

  public Type getType() {
    if (typeId == null)
      return Type.NIL_TYPE;
    return typeId.getType();
  }

  @Override
  public String generateCode() {
    return null;
  }
}
