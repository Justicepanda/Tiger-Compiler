package nonterminals;

import parser.ParserRule;
import symboltable.Type;

class TypeId extends ParserRule {
  private Type type;

  @Override
  public void parse() {
    if (peekTypeMatches("INT")) {
      type = new Type("int", "int");
      matchTerminal("INT");
    } else if (peekTypeMatches("STRING")) {
      type = new Type("string", "string");
      matchTerminal("STRING");
    } else {
      String id = matchIdAndGetValue();
      type = getType(id);
    }
  }

  @Override
  public String getLabel() {
    return "<type-id>";
  }

  public Type getType() {
    return type;
  }
}
