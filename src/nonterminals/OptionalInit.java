package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class OptionalInit extends ParserRule {
  private Constant constant;
  private Type type;

  @Override
  public void parse() {
    if (peekTypeMatches("ASSIGN")) {
      constant = new Constant();
      matchTerminal("ASSIGN");
      matchNonTerminal(constant);
      type = constant.getType();
    }
    else
      type = Type.NIL_TYPE;
  }

  @Override
  public String getLabel() {
    return "<optional-init>";
  }

  public String getValue() {
    if (constant == null)
      return "nil";
    return constant.getValue();
  }

  @Override
  public Type getType() {
    return type;
  }
}
