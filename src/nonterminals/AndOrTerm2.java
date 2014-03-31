package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class AndOrTerm2 extends ParserRule {
  private AndOrOp andOrOp;
  private EqualityTerm equalityTerm;
  private AndOrTerm2 andOrTerm2;
  private boolean expanded;
  private String op;

  @Override
  public void parse() {
    if (peekTypeMatches("AND") || peekTypeMatches("OR")) {
      expanded = true;
      if (peekTypeMatches("AND"))
        op = "and";
      else
        op = "or";
      matchOperator();
    }
  }

  private void matchOperator() {
    andOrOp = new AndOrOp();
    equalityTerm = new EqualityTerm();
    andOrTerm2 = new AndOrTerm2();
    storeLineNumber();
    matchNonTerminal(andOrOp);
    matchNonTerminal(equalityTerm);
    matchNonTerminal(andOrTerm2);
  }

  @Override
  public String getLabel() {
    return "<and-or-term2>";
  }

  @Override
  public Type getType() {
    return decideType(equalityTerm, andOrTerm2);
  }

  @Override
  protected String generateCode() {
    if (andOrTerm2.isExpanded()) {
      String op = andOrTerm2.getOp();
      String a = equalityTerm.generateCode();
      String b = andOrTerm2.generateCode();
      String temp = newTemp();
      emit(op + ", " + temp + ", " + a + ", " + b);
      return temp;
    }
    else {
      return equalityTerm.generateCode();
    }
  }

  public boolean isExpanded() {
    return expanded;
  }

  public String getOp() {
    return op;
  }
}
