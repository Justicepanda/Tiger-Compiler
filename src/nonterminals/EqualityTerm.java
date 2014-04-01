package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class EqualityTerm extends ParserRule {
  private final AddTerm addTerm = new AddTerm();
  private final EqualityTerm2 equalityTerm2 = new EqualityTerm2();

  @Override
  public void parse() {
    storeLineNumber();
    matchNonTerminal(addTerm);
    matchNonTerminal(equalityTerm2);
  }

  @Override
  public String getLabel() {
    return "<ineq-term>";
  }

  @Override
  public Type getType() {
    Type t = decideType(addTerm, equalityTerm2);
    if (equalityTerm2.isExpanded())
      return Type.INT_TYPE;
    else
      return t;
  }

  public boolean isExpanded() {
    return equalityTerm2.isExpanded();
  }

  public String getCodeOperation() {
    String op = equalityTerm2.getInverseOp();
    String a = addTerm.generateCode();
    String b = equalityTerm2.generateCode();
    return op + ", " + a + ", " + b;
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

  public boolean isConstant() {
    return addTerm.isConstant() && equalityTerm2.isConstant();
  }

  public int getValue() {
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
}
