package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class AddTerm extends ParserRule {
  private final MultTerm multTerm = new MultTerm();
  private final AddTerm2 addTerm2 = new AddTerm2();

  @Override
  public void parse() {
    storeLineNumber();
    matchNonTerminal(multTerm);
    matchNonTerminal(addTerm2);
  }

  @Override
  public String getLabel() {
    return "<add-term>";
  }

  @Override
  public Type getType() {
    return decideType(multTerm, addTerm2);
  }

  @Override
  public String generateCode() {
    if (addTerm2.isExpanded()) {
      String op = addTerm2.getOp();
      String a = multTerm.generateCode();
      String b = addTerm2.generateCode();
      String temp = newTemp();
      emit(op + ", " + temp + ", " + a + ", " + b);
      return temp;
    }
    else
      return multTerm.generateCode();
  }

  public boolean isConstant() {
    return multTerm.isConstant() && addTerm2.isConstant();
  }

  public int getValue() {
    if (addTerm2.getOp().equals("add"))
      return multTerm.getValue() + addTerm2.getValue();
    else if (addTerm2.getOp().equals("sub"))
      return multTerm.getValue() - addTerm2.getValue();
    else
      return multTerm.getValue();
  }
}
