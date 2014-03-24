package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class EqualityTermTail extends ParserRule {
  private final AddTermTail addTermTail = new AddTermTail();
  private final EqualityTerm2 equalityTerm2 = new EqualityTerm2();

  @Override
  public void parse() {
    storeLineNumber();
    matchNonTerminal(addTermTail);
    matchNonTerminal(equalityTerm2);
  }

  @Override
  public String getLabel() {
    return "<ineq-term-tail>";
  }

  @Override
  public Type getType() {
    return decideType(addTermTail, equalityTerm2);
  }
}
