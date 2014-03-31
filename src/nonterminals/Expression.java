package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class Expression extends ParserRule {
  private Type type;
  private Expression expression;
  private AndOrTerm andOrTerm;

  @Override
  public void parse() {
    if (peekTypeMatches("MINUS"))
      matchMinus();
    else
      matchAndOr();
   }

  private void matchMinus() {
    expression = new Expression();
    matchTerminal("MINUS");
    matchNonTerminal(expression);
    type = expression.getType();
  }

  private void matchAndOr() {
    andOrTerm = new AndOrTerm();
    matchNonTerminal(andOrTerm);
    type = andOrTerm.getType();
  }

  @Override
  public String getLabel() {
    return "<expr>";
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  protected String generateCode() {
    return andOrTerm.generateCode();
  }

  public String getCodeEqualityOperation() {
    return andOrTerm.getCodeEqualityOperation();
  }

  public boolean hasEqualityOperation() {
    return andOrTerm.hasEqualityOperation();
  }
}
