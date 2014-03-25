package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class AddTermTail extends ParserRule {
  private final MultTermTail multTermTail = new MultTermTail();
  private final AddTerm2 addTerm2 = new AddTerm2();
  private Type type;

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
    this.type = decideType(multTermTail, addTerm2);
    return decideType(multTermTail, addTerm2);
  }
}
