package nonterminals;

import parser.ParserRule;
import symboltable.Type;

import java.util.List;

public class StatId extends ParserRule {
  private Type type;
  private ExpressionList expressionList;
  private LValue lValue;
  private StatIdTail statIdTail;

  @Override
  public void parse() {
    if (peekTypeMatches("LPAREN"))
      matchExpressionList();
    else
      matchAssignment();
  }

  private void matchExpressionList() {
    expressionList = new ExpressionList();
    matchTerminal("LPAREN");
    matchNonTerminal(expressionList);
    matchTerminal("RPAREN");
  }

  private void matchAssignment() {
    lValue = new LValue();
    this.statIdTail = new StatIdTail();
    matchNonTerminal(lValue);
    matchTerminal("ASSIGN");
    matchNonTerminal(statIdTail);
    type = statIdTail.getType();
  }

  public String getLeftArrayIndex() {
    return lValue.generateCode();
  }

  @Override
  public String getLabel() {
    return "<stat-id>";
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public String generateCode() {
    if (statIdTail == null) {
      return expressionList.generateCode();
    }
    else {
      return statIdTail.generateCode();
    }
  }

  public boolean isFunction() {
    return expressionList != null;
  }

  public boolean isReturnedFunction() {
    return statIdTail.isReturnedFunction();
  }

  public List<Expression> getParameters() {
    if (expressionList != null)
      return expressionList.getExpressions();
    else
      return statIdTail.getParameters();
  }

  public String printParameters() {
    String res = "";
    for (Expression expression: getParameters())
      res += expression.generateCode();
    return res;
  }

  public String getFunctionId() {
    return statIdTail.getFunctionId();
  }

  public boolean hasParameters() {
    return !getParameters().isEmpty();
  }

  public void setDimensions(List<Integer> dimensions) {
    lValue.setDimensions(dimensions);
  }
}
