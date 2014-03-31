package nonterminals.statement;

import nonterminals.Expression;
import parser.ParserRule;
import symboltable.Type;

import java.util.Stack;

public abstract class Statement extends ParserRule {
  private static Stack<String> endLabels = new Stack<String>();

  protected static void addEndLoopLabel(String label) {
    endLabels.push(label);
  }

  protected static String getLastEndLoopLabel() {
    return endLabels.peek();
  }

  protected static void removeMostRecentEndLabel() {
    endLabels.pop();
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
