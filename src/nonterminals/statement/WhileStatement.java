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
    emitLabel(startWhile + ":");
    if (expression.hasEqualityOperation())
      emit(expression.getCodeEqualityOperation() + ", " + afterWhile);
    else {
      String id = expression.generateCode();
      emit("breq, " + id + ", 0, " + afterWhile);
    }
    statSequence.generateCode();
    emit("goto, " + startWhile + ", , ");
    emitLabel(afterWhile + ":");
    removeMostRecentEndLabel();
    return null;
  }
}
