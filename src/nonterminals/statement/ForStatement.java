package nonterminals.statement;

import nonterminals.Expression;
import nonterminals.StatSequence;
import parser.SemanticTypeException;

public class ForStatement extends Statement {
  private Expression expression;
  private Expression expression2;
  private StatSequence statSequence;
  private String id;

  @Override
  public Expression getExpression() {
    return expression;
  }

  @Override
  public void parse() {
    expression = new Expression();
    expression2 = new Expression();
    statSequence = new StatSequence();
    matchTerminal("FOR");
    id = matchIdAndGetValue();
    matchTerminal("ASSIGN");
    matchNonTerminal(expression);
    matchTerminal("TO");
    matchNonTerminal(expression2);
    matchTerminal("DO");
    matchNonTerminal(statSequence);
    matchTerminal("ENDDO");
    matchTerminal("SEMI");
    semanticCheck();
  }

  private void semanticCheck() {
    if (expression.isConstant() && expression2.isConstant()) {
      if (expression.getValue() >= expression2.getValue())
        generateException();
    }
  }

  @Override
  public String generateCode() {
    String startLabel = newLabel("start_loop");
    String endLabel = newLabel("end_loop");
    addEndLoopLabel(endLabel);
    emit("assign, " + id + ", " + expression.generateCode());
    emitLabel(startLabel + ":");
    emit("brgeq, " + id + ", " + expression2.generateCode() + ", " + endLabel);
    statSequence.generateCode();
    String temp = newTemp();
    emit("add, " + temp + ", " + id + ", 1");
    emit("assign, " + id + ", " + temp + ", ");
    emit("goto, " + startLabel + ", , ");
    emitLabel(endLabel + ":");
    removeMostRecentEndLabel();
    return null;
  }
}
