package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class AndOrTerm extends ParserRule {
  private final EqualityTerm equalityTerm = new EqualityTerm();
  private final AndOrTerm2 andOrTerm2 = new AndOrTerm2();

  @Override
  public void parse() {
    storeLineNumber();
    matchNonTerminal(equalityTerm);
    matchNonTerminal(andOrTerm2);
  }

  @Override
  public String getLabel() {
    return "<and-or-term>";
  }

  @Override
  public Type getType() {
    return decideType(equalityTerm, andOrTerm2);
  }

  @Override
  public String generateCode() {
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

  public String getCodeEqualityOperation() {
    return equalityTerm.getCodeOperation();
  }

  public boolean hasEqualityOperation() {
    return equalityTerm.isExpanded();
  }
}
