package nonterminals;

import parser.ParserRule;
import symboltable.Type;

import java.util.ArrayList;
import java.util.List;

public class ExpressionListTail extends ParserRule {
  private Expression expr;
  private ExpressionListTail expressionListTail;

  @Override
  public void parse() {
    if (peekTypeMatches("COMMA"))
      matchList();
  }

  private void matchList() {
    expr = new Expression();
    expressionListTail = new ExpressionListTail();
    matchTerminal("COMMA");
    matchNonTerminal(expr);
    matchNonTerminal(expressionListTail);
  }

  @Override
  public String getLabel() {
    return "<expr-list-tail>";
  }

  public List<Expression> getExpressions() {
    if (expr == null)
      return null;
    List<Expression> listSoFar = expressionListTail.getExpressions();
    if (listSoFar == null)
      listSoFar = new ArrayList<Expression>();
    listSoFar.add(expr);
    return listSoFar;
  }

  @Override
  public Type getType() {
    return null;
  }

  @Override
  public String generateCode() {
    return null;
  }
}
