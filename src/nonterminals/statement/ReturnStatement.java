package nonterminals.statement;

import nonterminals.Expression;

public class ReturnStatement extends Statement {
  private Expression expression;

  @Override
  public void parse() {
    expression = new Expression();
    matchTerminal("RETURN");
    matchNonTerminal(expression);
    matchTerminal("SEMI");
  }

  @Override
  public String generateCode() {
    emit("return, " + expression.generateCode() + ", , ");
    return null;
  }

  @Override
  public Expression getExpression() {
    return expression;
  }
}
