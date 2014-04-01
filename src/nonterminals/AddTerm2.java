package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class AddTerm2 extends ParserRule {
  private MultTerm multTerm;
  private AddTerm2 addTerm2;
  private AddOp addOp;
  private boolean isExpanded;
  private String op = "";

  @Override
  public void parse() {
    if (peekTypeMatches("PLUS") || peekTypeMatches("MINUS")) {
      if (peekTypeMatches("PLUS"))
        op = "add";
      else
        op = "sub";
      isExpanded = true;
      matchOperator();
    }
  }

  private void matchOperator() {
    addOp = new AddOp();
    multTerm = new MultTerm();
    addTerm2 = new AddTerm2();
    storeLineNumber();
    matchNonTerminal(addOp);
    matchNonTerminal(multTerm);
    matchNonTerminal(addTerm2);
  }

  @Override
  public String getLabel() {
    return "<add-term2>";
  }

  public Type getType() {
    return decideType(multTerm, addTerm2);
  }

  @Override
  public String generateCode() {
    if (addTerm2.isExpanded) {
      String op = addTerm2.getOp();
      String a = multTerm.generateCode();
      String b = addTerm2.generateCode();
      String temp = newTemp();
      emit(op + ", " + temp + ", " + a + ", " + b);
      return temp;
    }
    else {
      return multTerm.generateCode();
    }
  }

  public boolean isExpanded() {
    return isExpanded;
  }

  public String getOp() {
    return op;
  }

  public boolean isConstant() {
    if (isExpanded)
      return multTerm.isConstant() && addTerm2.isConstant();
    else
      return true;
  }

  public int getValue() {
    if (isExpanded) {
      if (addTerm2.getOp().equals("add"))
        return multTerm.getValue() + addTerm2.getValue();
      else if (addTerm2.getOp().equals("sub"))
        return multTerm.getValue() - addTerm2.getValue();
      else
        return multTerm.getValue();
    }
    else
      return 0;
  }
}
