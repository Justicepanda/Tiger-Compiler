package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class AndOrTermTail extends ParserRule {
  private final EqualityTermTail equalityTermTail = new EqualityTermTail();
  private final AndOrTerm2 andOrTerm2 = new AndOrTerm2();

  @Override
  public void parse() {
    storeLineNumber();
    matchNonTerminal(equalityTermTail);
    matchNonTerminal(andOrTerm2);
  }

  @Override
  public String getLabel() {
    return "<and-or-term-tail>";
  }

  @Override
  public Type getType() {
    return decideType(equalityTermTail, andOrTerm2);
  }

  public void setId(String id) {
    equalityTermTail.setId(id);
  }

  @Override
  public String generateCode() {
    if (andOrTerm2.isExpanded()) {
      String op = andOrTerm2.getOp();
      String a = equalityTermTail.generateCode();
      String b = andOrTerm2.generateCode();
      String temp = newTemp();
      emit(op + ", " + temp + ", " + a + ", " + b);
      return temp;
    }
    else {
      return equalityTermTail.generateCode();
    }
  }

  public boolean isArray() {
    return equalityTermTail.isArray();
  }
}
