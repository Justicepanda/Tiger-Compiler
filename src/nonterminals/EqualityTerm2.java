package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class EqualityTerm2 extends ParserRule {
  private AddTerm addTerm;
  private EqualityTerm2 equalityTerm2;
  private EqualityOp equalityOp;
  private boolean wasExpanded;

  @Override
  public void parse() {
    if (isEqualityTerm())
      matchOperator();
  }

  private void matchOperator() {
    wasExpanded = true;
    storeLineNumber();
    equalityOp = new EqualityOp();
    addTerm = new AddTerm();
    equalityTerm2 = new EqualityTerm2();
    matchNonTerminal(equalityOp);
    matchNonTerminal(addTerm);
    matchNonTerminal(equalityTerm2);
  }

  private boolean isEqualityTerm() {
    return peekTypeMatches("NEQ") || peekTypeMatches("EQ")
            || peekTypeMatches("LESSER") || peekTypeMatches("GREATER")
            || peekTypeMatches("LESSEREQ") || peekTypeMatches("GREATEREQ");
  }

  @Override
  public String getLabel() {
    return "<ineq-term-2>";
  }

  @Override
  public Type getType() {
    return decideType(addTerm, equalityTerm2);
  }

  @Override
  public String generateCode() {
    if (equalityTerm2.isExpanded()) {
      String falseLabel = newLabel("false");
      String trueLabel = newLabel("true");
      String doneLabel = newLabel("done");
      String temp = newTemp();
      emit(equalityTerm2.getInverseOp() + ", " + addTerm.generateCode() + ", " + equalityTerm2.generateCode() + ", " + falseLabel);
      emit("breq, 0, 0, " + trueLabel);
      emit(falseLabel + ":");
      emit("assign, " + temp + ", 0, ");
      emit("breq, 0, 0, " + doneLabel);
      emit(trueLabel + ":");
      emit("assign, " + temp + ", 1, ");
      emit(doneLabel + ":");
      return temp;
    }
    else {
      return addTerm.generateCode();
    }
  }

  public boolean isExpanded() {
    return wasExpanded;
  }

  public String getInverseOp() {
    return equalityOp.getInverseOp();
  }

  public String getOp() {
    if (equalityOp != null)
      return equalityOp.getOp();
    else
      return "";
  }

  public boolean isConstant() {
    if (isExpanded())
      return addTerm.isConstant() && equalityTerm2.isConstant();
    else
      return true;
  }

  public int getValue() {
    if (isExpanded()) {
      if (!equalityTerm2.isExpanded())
        return addTerm.getValue();
      if (equalityTerm2.getOp().equals("eq")) {
        if (addTerm.getValue() == equalityTerm2.getValue())
          return 1;
        else
          return 0;
      }
      else if (equalityTerm2.getOp().equals("neq")) {
        if (addTerm.getValue() != equalityTerm2.getValue())
          return 1;
        else
          return 0;
      }
      else if (equalityTerm2.getOp().equals("less")) {
        if (addTerm.getValue() < equalityTerm2.getValue())
          return 1;
        else
          return 0;
      }
      else if (equalityTerm2.getOp().equals("great")) {
        if (addTerm.getValue() > equalityTerm2.getValue())
          return 1;
        else
          return 0;
      }
      else if (equalityTerm2.getOp().equals("leq")) {
        if (addTerm.getValue() <= equalityTerm2.getValue())
          return 1;
        else
          return 0;
      }
      else if (equalityTerm2.getOp().equals("geq")) {
        if (addTerm.getValue() >= equalityTerm2.getValue())
          return 1;
        else
          return 0;
      }
      else
        return addTerm.getValue();
    }
    else
      return -1;
  }
}
