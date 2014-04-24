package nonterminals.statement;

import nonterminals.Expression;
import nonterminals.StatSequence;
import nonterminals.StatTail;
import symboltable.Type;

public class IfStatement extends Statement {
  private Expression expression;
  private StatSequence statSequence;
  private StatTail statTail;

  @Override
  public Expression getExpression() {
    return expression;
  }

  @Override
  public void parse() {
    expression = new Expression();
    statSequence = new StatSequence();
    statTail = new StatTail();
    matchTerminal("IF");
    matchNonTerminal(expression);
    if (!(expression.getType().isExactlyOfType(Type.INT_TYPE)))
      generateException();
    matchTerminal("THEN");
    matchNonTerminal(statSequence);
    matchNonTerminal(statTail);
  }

  @Override
  public String generateCode() {
    String endLabel = newLabel("after_if");
    String temp = expression.generateCode();
    emit("breq, " + temp + ", 0, " + endLabel);
    statSequence.generateCode();
    emitLabel(endLabel + ":");
    return null;
  }
}
