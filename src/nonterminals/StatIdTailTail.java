package nonterminals;

import parser.ParserRule;
import symboltable.Type;

import java.util.ArrayList;
import java.util.List;

public class StatIdTailTail extends ParserRule {
  private ExpressionList expressionList;
  private Type type;
  private AndOrTermTail andOrTermTail;

  @Override
  public void parse() {
    if (peekTypeMatches("LPAREN"))
      matchList();
    else
      matchTail();
  }

  private void matchTail() {
    andOrTermTail = new AndOrTermTail();
    matchNonTerminal(andOrTermTail);
    type = andOrTermTail.getType();
  }

  private void matchList() {
    expressionList = new ExpressionList();
    matchTerminal("LPAREN");
    matchNonTerminal(expressionList);
    matchTerminal("RPAREN");
    type = Type.NIL_TYPE;
  }

  @Override
  public String getLabel() {
    return "<stat-id-tail-tail>";
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  protected String generateCode() {
    return null;
  }

  public List<Expression> getParameters() {
    if (expressionList == null)
      return new ArrayList<Expression>();
    return expressionList.getExpressions();
  }

}
