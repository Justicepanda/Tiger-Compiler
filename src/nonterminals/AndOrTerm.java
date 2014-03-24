package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;

public class AndOrTerm extends ParserRule {
  private final EqualityTerm equalityTerm = new EqualityTerm();
  private final AndOrTerm2 andOrTerm2 = new AndOrTerm2();
  private int lineNumber;

  @Override
  public void parse() {
    lineNumber = getLineNumber();
    matchNonTerminal(equalityTerm);
    matchNonTerminal(andOrTerm2);
  }

  @Override
  public String getLabel() {
    return "<and-or-term>";
  }

  @Override
  public Type getType() {
    if (andOrTerm2.getType() != null && equalityTerm != null && equalityTerm.getType() != null) {
      if (equalityTerm.getType().isOfSameType(andOrTerm2.getType()))
        return equalityTerm.getType();
      throw new SemanticTypeException(lineNumber);
    } else if (equalityTerm != null && equalityTerm.getType() != null && andOrTerm2.getType() == null)
      return equalityTerm.getType();
    else if (andOrTerm2 != null && andOrTerm2.getType() != null && equalityTerm.getType() == null)
      return equalityTerm.getType();
    return null;
  }
}
