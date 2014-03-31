package nonterminals.statement;

import nonterminals.Expression;
import nonterminals.StatSequence;
import symboltable.Type;

public class WhileStatement extends Statement {
  private Expression expression;
  private StatSequence statSequence;

  @Override
  public Expression getExpression() {
    return expression;
  }

  @Override
  public void parse() {
    expression = new Expression();
    statSequence = new StatSequence();
    matchTerminal("WHILE");
    matchNonTerminal(expression);
    if (!(expression.getType().isExactlyOfType(Type.INT_TYPE)))
      generateException();
    matchTerminal("DO");
    matchNonTerminal(statSequence);
    matchTerminal("ENDDO");
    matchTerminal("SEMI");
  }

  @Override
  public String generateCode() {
    String startWhile = newLabel("start_while");
    String afterWhile = newLabel("after_while");
    addEndLoopLabel(afterWhile);
    emit(startWhile + ":");
    if (expression.hasEqualityOperation())
      emit(expression.getCodeEqualityOperation() + ", " + afterWhile);
    statSequence.generateCode();
    emit("goto, " + startWhile + ", , ");
    emit(afterWhile + ":");
    return null;
  }
}
