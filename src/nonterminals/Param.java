package nonterminals;

import parser.ParserRule;
import symboltable.Argument;
import symboltable.Type;

public class Param extends ParserRule {
  private final TypeId typeId = new TypeId();
  private String id;

  @Override
  public void parse() {
    id = matchIdAndGetValue();
    matchTerminal("COLON");
    matchNonTerminal(typeId);
  }

  @Override
  public String getLabel() {
    return "<param>";
  }

  public Argument getArgument() {
    return new Argument(typeId.getType(), id);
  }

  @Override
  public Type getType() {
    return typeId.getType();
  }

  @Override
  public String generateCode() {
    return null;
  }
}
