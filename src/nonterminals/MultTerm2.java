package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class MultTerm2 extends ParserRule {
  private Factor factor;
  private MultTerm2 multTerm2;
  private MultOp multOp;
  private boolean expanded;
  private String op = "";


  @Override
  public void parse() {
    if (peekTypeMatches("MULT") || peekTypeMatches("DIV")) {
      expanded = true;
      if (peekTypeMatches("MULT"))
        op = "mult";
      else
        op = "div";
      matchMultOp();
    }
  }

  private void matchMultOp() {
    multOp = new MultOp();
    factor = new Factor();
    multTerm2 = new MultTerm2();
    storeLineNumber();
    matchNonTerminal(multOp);
    matchNonTerminal(factor);
    matchNonTerminal(multTerm2);
  }

  @Override
  public String getLabel() {
    return "<mult-term2>";
  }

  @Override
  public Type getType() {
    return decideType(factor, multTerm2);
  }

  @Override
  public String generateCode() {
    if (multTerm2.isExpanded()) {
      String op = multTerm2.getOp();
      String a = factor.generateCode();
      String b = multTerm2.generateCode();
      String temp = newTemp();
      emit(op + ", " + temp + ", " + a + ", " + b);
      return temp;
    }
    else {
      return factor.generateCode();
    }
  }

  public boolean isExpanded() {
    return expanded;
  }

  public String getOp() {
    return op;
  }

  public boolean isConstant() {
    if (isExpanded())
      return factor.isConstant() && multTerm2.isConstant();
    else
      return true;
  }

  public int getValue() {
    if (isExpanded()) {
      if (multTerm2.getOp().equals("mult"))
        return factor.getValue() * multTerm2.getValue();
      else
        return factor.getValue() / multTerm2.getValue();
    }
    else
      return 1;
  }
}
