package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class Factor extends ParserRule {
  private Type type;
  private Expression expression;
  private Constant constant;
  private Factor factor;
  private LValue lValue;
  private String id;

  @Override
  public void parse() {
    if (peekTypeMatches("LPAREN"))
      matchParenthesizedExpression();
    else if (isConstant())
      matchConstant();
    else if (peekTypeMatches("MINUS"))
      matchNegativeFactor();
    else
      matchVariable();
  }

  private void matchParenthesizedExpression() {
    expression = new Expression();
    matchTerminal("LPAREN");
    matchNonTerminal(expression);
    matchTerminal("RPAREN");
    type = expression.getType();
  }

  private boolean isConstant() {
    return peekTypeMatches("INTLIT") || peekTypeMatches("STRLIT")
            || peekTypeMatches("NIL");
  }

  private void matchConstant() {
    constant = new Constant();
    matchNonTerminal(constant);
    type = constant.getType();
  }

  private void matchNegativeFactor() {
    factor = new Factor();
    matchTerminal("MINUS");
    matchNonTerminal(factor);
    type = factor.getType();
  }

  private void matchVariable() {
    lValue = new LValue();
    id = matchIdAndGetValue();
    matchNonTerminal(lValue);
    if (lValue.getType().isOfSameType(Type.NIL_TYPE))
      type = getTypeOfVariable(id);
    else
      type = lValue.getType();
  }

  @Override
  public String getLabel() {
    return "<factor>";
  }

  @Override
  protected Type getType() {
    return type;
  }

  @Override
  protected String generateCode() {
    if (constant != null)
      return constant.generateCode();
    else if (expression != null)
      return expression.generateCode();
    return id;
  }
}
