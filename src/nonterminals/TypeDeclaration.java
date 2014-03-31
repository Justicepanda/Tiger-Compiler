package nonterminals;

import parser.ParserRule;

class TypeDeclaration extends ParserRule {
  private final Type type = new Type();

  @Override
  public void parse() {
    matchTerminal("TYPE");
    String id = matchIdAndGetValue();
    matchTerminal("EQ");
    matchNonTerminal(type);
    matchTerminal("SEMI");
    addType(id, type.getType());
  }

  @Override
  public String getLabel() {
    return "<type-declaration>";
  }

  @Override
  public symboltable.Type getType() {
    return type.getType();
  }

  @Override
  public String generateCode() {
    return null;
  }
}
