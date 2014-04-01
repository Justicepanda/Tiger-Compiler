package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class MultTerm extends ParserRule {
  private final Factor factor = new Factor();
  private final MultTerm2 multTerm2 = new MultTerm2();

  @Override
  public void parse() {
    storeLineNumber();
    matchNonTerminal(factor);
    matchNonTerminal(multTerm2);
  }

  @Override
  public String getLabel() {
    return "<mult-term>";
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
    return factor.generateCode();
  }

  public boolean isConstant() {
    return factor.isConstant() && multTerm2.isConstant();
  }

  public int getValue() {
    if (multTerm2.getOp().equals("mult"))
      return factor.getValue() * multTerm2.getValue();
    else if (multTerm2.getOp().equals("div"))
      return factor.getValue() / multTerm2.getValue();
    else
      return factor.getValue();
  }
}
