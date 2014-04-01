package nonterminals;

import parser.ParserRule;
import symboltable.Type;

import java.util.List;

public class Factor extends ParserRule {
  private Type type;
  private Expression expression;
  private Constant constant;
  private Factor factor;
  private LValue lValue;
  private String id;
  private boolean isConstant;

  @Override
  public void parse() {
    if (peekTypeMatches("LPAREN"))
      matchParenthesizedExpression();
    else if (checkConstant())
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

  private boolean checkConstant() {
    if (peekTypeMatches("INTLIT") || peekTypeMatches("STRLIT")
            || peekTypeMatches("NIL")) {
      isConstant = true;
      return true;
    }
    return false;
  }

  public boolean isConstant() {
    return isConstant;
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
  public String generateCode() {
    if (constant != null)
      return constant.generateCode();
    else if (expression != null)
      return expression.generateCode();
    if (id != null) {
      List<Integer> dimensions = getVariable(id).getType().getDimensions();
      if (dimensions != null)
        lValue.setDimensions(dimensions);
      String code = lValue.generateCode();
      if (code != null) {
        String newTemp = newTemp();
        emit("array_load, " + newTemp + ", " + id + ", " + code);
        return newTemp;
      }
    }
    return id;
  }

  public int getValue() {
    if (expression != null)
      return expression.getValue();
    else if (factor != null)
      return -factor.getValue();
    else if (constant != null)
      return constant.getIntegerValue();
    return 0;
  }
}
