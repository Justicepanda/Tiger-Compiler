package nonterminals;

import parser.ParserRule;

class Type extends ParserRule {
  private TypeId typeId;

  @Override
  public void parse() {
    if (peekTypeMatches("ARRAY")) {
      typeId = new TypeId();
      matchTerminal("ARRAY");
      matchTerminal("LBRACK");
      matchTerminal("INTLIT");
      matchTerminal("RBRACK");
      matchTerminal("OF");
      matchNonTerminal(typeId);
    } else {
      typeId = new TypeId();
      matchNonTerminal(typeId);
    }
  }

  @Override
  public String getLabel() {
    return "<type>";
  }

  @Override
  public symboltable.Type getType() {
    return typeId.getType();
  }
}
