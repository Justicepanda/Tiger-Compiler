package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class Expression extends ParserRule {
  private Type type;
  private Expression expression;
  private AndOrTerm andOrTerm;

  @Override
  public void parse() {
    if (peekTypeMatches("MINUS")) {
      expression = new Expression();
      matchTerminal("MINUS");
      matchNonTerminal(expression);
      type = expression.getType();
    } else {
      andOrTerm = new AndOrTerm();
      matchNonTerminal(andOrTerm);
      type = andOrTerm.getType();
    }
  }

  @Override
  public String getLabel() {
    return "<expr>";
  }

  @Override
  public Type getType() {
    return type;
  }

  public Integer getValue() {
    return null;
  }
}
