package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;

public class EqualityTermTail extends ParserRule {
  private final AddTermTail addTermTail = new AddTermTail();
  private final EqualityTerm2 equalityTerm2 = new EqualityTerm2();
  private int lineNumber;

  @Override
  public void parse() {
    lineNumber = getLineNumber();
    matchNonTerminal(addTermTail);
    matchNonTerminal(equalityTerm2);
  }

  @Override
  public String getLabel() {
    return "<ineq-term-tail>";
  }

  @Override
  public Type getType() {
    if (equalityTerm2 != null && equalityTerm2.getType() != null && addTermTail != null && addTermTail.getType() != null) {
      if (addTermTail.getType().isOfSameType(equalityTerm2.getType()))
        return addTermTail.getType();
      throw new SemanticTypeException(lineNumber);
    } else if (addTermTail != null && addTermTail.getType() != null && equalityTerm2.getType() == null)
      return addTermTail.getType();
    else if (equalityTerm2 != null && equalityTerm2.getType() != null && addTermTail.getType() == null)
      return equalityTerm2.getType();
    return null;
  }
}
