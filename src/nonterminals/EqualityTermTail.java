package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
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
    if (equalityTerm2.getType() != null && addTermTail.getType() != null) {
      if (addTermTail.getType().isOfSameType(equalityTerm2.getType()))
        return addTermTail.getType();
      generateException();
    } else if (addTermTail.getType() != null)
      return addTermTail.getType();
    else if (equalityTerm2.getType() != null)
      return equalityTerm2.getType();
    return null;
  }
}
