package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
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
    if (andOrTerm2.getType() != null && equalityTerm.getType() != null) {
      if (equalityTerm.getType().isOfSameType(andOrTerm2.getType()))
        return equalityTerm.getType();
      generateException();
    } else if (equalityTerm.getType() != null)
      return equalityTerm.getType();
    else if (andOrTerm2.getType() != null)
      return equalityTerm.getType();
    return null;
  }
}
