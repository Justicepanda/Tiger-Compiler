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
    String op = equalityTerm2.getOp();
    String a = addTerm.generateCode();
    String b = equalityTerm2.generateCode();
    return op + ", " + a + ", " + b;
  }

  @Override
  public String generateCode() {
    if (equalityTerm2.isExpanded()) {
      return "";
    }
    else {
      return addTerm.generateCode();
    }
  }
}
