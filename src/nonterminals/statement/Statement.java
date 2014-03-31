package nonterminals.statement;

import nonterminals.Expression;
import parser.ParserRule;
import symboltable.Type;

public abstract class Statement extends ParserRule {
  private static String lastLoopEndLabel = "";

  protected static void addEndLoopLabel(String label) {
    lastLoopEndLabel = label;
  }

  protected static String getLastEndLoopLabel() {
    return lastLoopEndLabel;
  }

  @Override
  protected String getLabel() {
    return "<stat>";
  }

  public abstract Expression getExpression();

  @Override
  protected Type getType() {
    return null;
  }
}
