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

  @Override
  public String getLabel() {
    return "<stat-id>";
  }

  @Override
  public Type getType() {
    return type;
  }

  public boolean isFunction() {
    return expressionList != null;
  }

  public List<Expression> getParameters() {
    return expressionList.getExpressions();
  }
}
