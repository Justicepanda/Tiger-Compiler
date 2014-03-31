package nonterminals;

import parser.ParserRule;
import symboltable.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpressionList extends ParserRule {
  private Expression expression;
  private ExpressionListTail expressionListTail;

  @Override
  public void parse() {
    if (hasAppropriateFirstTerminal())
      matchExpression();
  }

  private void matchExpression() {
    expression = new Expression();
    expressionListTail = new ExpressionListTail();
    matchNonTerminal(expression);
    matchNonTerminal(expressionListTail);
  }

  private boolean hasAppropriateFirstTerminal() {
    return peekTypeMatches("ID") || peekTypeMatches("LPAREN")
            || peekTypeMatches("INTLIT") || peekTypeMatches("STRLIT")
            || peekTypeMatches("NIL");
  }

  @Override
  public String getLabel() {
    return "<expr-list>";
  }

  public List<Expression> getExpressions() {
    if (expression == null)
      return new ArrayList<Expression>();
    List<Expression> listSoFar = expressionListTail.getExpressions();
    if (listSoFar == null)
      listSoFar = new ArrayList<Expression>();
    listSoFar.add(expression);
    Collections.reverse(listSoFar);
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
