package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;

public class EqualityTerm extends ParserRule {
  private final AddTerm addTerm = new AddTerm();
  private final EqualityTerm2 equalityTerm2 = new EqualityTerm2();
  private int lineNumber;

  @Override
  public void parse() {
    lineNumber = getLineNumber();
    matchNonTerminal(addTerm);
    matchNonTerminal(equalityTerm2);
  }

  @Override
  public String getLabel() {
    return "<ineq-term>";
  }

  @Override
  public Type getType() {
    if (equalityTerm2 != null && equalityTerm2.getType() != null && addTerm != null && addTerm.getType() != null) {
      if (addTerm.getType().isOfSameType(equalityTerm2.getType()))
        return addTerm.getType();
      throw new SemanticTypeException(lineNumber);
    } else if (addTerm != null && addTerm.getType() != null && equalityTerm2.getType() == null)
      return addTerm.getType();
    else if (equalityTerm2 != null && equalityTerm2.getType() != null && addTerm.getType() == null)
      return equalityTerm2.getType();
    return null;
  }
}
