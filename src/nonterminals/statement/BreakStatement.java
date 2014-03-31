package nonterminals.statement;

import nonterminals.Expression;

public class BreakStatement extends Statement {
  @Override
  public Expression getExpression() {
    return null;
  }

  @Override
  public void parse() {
    matchTerminal("BREAK");
    matchTerminal("SEMI");
  }

  @Override
  public String generateCode() {
    emit("goto, " + getLastEndLoopLabel() + ", , ");
    return null;
  }
}
