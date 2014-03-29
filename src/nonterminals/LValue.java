package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class LValue extends ParserRule {
  private Expression expression;
  private LValue lValue;

  @Override
  public void parse() {
    if (peekTypeMatches("LBRACK"))
      matchLValue();
  }

  private void matchLValue() {
    expression = new Expression();
    lValue = new LValue();
    matchTerminal("LBRACK");
    matchNonTerminal(expression);
    matchTerminal("RBRACK");
    matchNonTerminal(lValue);
  }

  @Override
  public String getLabel() {
    return "<lvalue>";
  }

  @Override
  public Type getType() {
    return Type.NIL_TYPE;
  }
}
