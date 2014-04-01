package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class AddTermTail extends ParserRule {
  private final MultTermTail multTermTail = new MultTermTail();
  private final AddTerm2 addTerm2 = new AddTerm2();

  @Override
  public void parse() {
    storeLineNumber();
    matchNonTerminal(multTermTail);
    matchNonTerminal(addTerm2);
  }

  @Override
  public String getLabel() {
    return "<add-term-tail>";
  }

  @Override
  public Type getType() {
    return decideType(multTermTail, addTerm2);
  }

  public void setId(String id) {
    multTermTail.setId(id);
  }

  @Override
  public String generateCode() {
    if (addTerm2.isExpanded()) {
      String op = addTerm2.getOp();
      String a = multTermTail.generateCode();
      String b = addTerm2.generateCode();
      String temp = newTemp();
      emit(op + ", " + temp + ", " + a + ", " + b);
      return temp;
    }
    else
      return multTermTail.generateCode();
  }

  public boolean isArray() {
    return multTermTail.isArray();
  }
}
