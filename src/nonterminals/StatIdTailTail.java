package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Argument;
import symboltable.Type;

import java.util.ArrayList;
import java.util.List;

public class StatIdTailTail extends ParserRule {
  private ExpressionList expressionList;
  private Type type;
  private AndOrTermTail andOrTermTail;

  @Override
  public void parse() {
    if (peekTypeMatches("LPAREN")) {
      expressionList = new ExpressionList();
      matchTerminal("LPAREN");
      matchNonTerminal(expressionList);
      matchTerminal("RPAREN");
      type = Type.NIL_TYPE;
    } else {
      andOrTermTail = new AndOrTermTail();
      matchNonTerminal(andOrTermTail);
      type = andOrTermTail.getType();
    }
  }

  @Override
  public String getLabel() {
    return "<stat-id-tail-tail>";
  }

  @Override
  public Type getType() {
    return type;
  }

  public List<Expression> getParameters() {
    if (expressionList == null)
      return new ArrayList<Expression>();
    return expressionList.getExpressions();
  }

}
