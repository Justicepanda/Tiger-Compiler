package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
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
    if (equalityTerm2.getType() != null && addTerm.getType() != null) {
      if (addTerm.getType().isOfSameType(equalityTerm2.getType()))
        return addTerm.getType();
      generateException();
    } else if (addTerm.getType() != null)
      return addTerm.getType();
    else if (equalityTerm2.getType() != null)
      return equalityTerm2.getType();
    return null;
  }
}
