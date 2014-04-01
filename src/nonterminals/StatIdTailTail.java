package nonterminals;

import parser.ParserRule;
import symboltable.Type;

import java.util.ArrayList;
import java.util.List;

public class StatIdTailTail extends ParserRule {
  private ExpressionList expressionList;
  private Type type;
  private AndOrTermTail andOrTermTail;
  private boolean function;

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
    function = true;
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

  public void setId(String id) {
    if (andOrTermTail != null)
      andOrTermTail.setId(id);
  }

  @Override
  public String generateCode() {
    if (andOrTermTail != null)
      return andOrTermTail.generateCode();
    else
      return null;
  }

  public List<Expression> getParameters() {
    if (expressionList == null)
      return new ArrayList<Expression>();
    return expressionList.getExpressions();
  }

  public boolean isFunction() {
    return function;
  }

  public boolean isArray() {
    return andOrTermTail.isArray();
  }
}
