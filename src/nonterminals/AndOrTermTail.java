package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;

public class AndOrTermTail extends ParserRule {
  private final EqualityTermTail equalityTermTail = new EqualityTermTail();
  private final AndOrTerm2 andOrTerm2 = new AndOrTerm2();
  private int lineNumber;

  @Override
  public void parse() {
    lineNumber = getLineNumber();
    matchNonTerminal(equalityTermTail);
    matchNonTerminal(andOrTerm2);
  }

  @Override
  public String getLabel() {
    return "<and-or-term-tail>";
  }

  @Override
  public Type getType() {
    if (andOrTerm2 != null && andOrTerm2.getType() != null && equalityTermTail != null && equalityTermTail.getType() != null) {
      if (equalityTermTail.getType().isOfSameType(andOrTerm2.getType()))
        return equalityTermTail.getType();
      throw new SemanticTypeException(lineNumber);
    } else if (equalityTermTail != null && equalityTermTail.getType() != null && andOrTerm2.getType() == null)
      return equalityTermTail.getType();
    else if (andOrTerm2 != null && andOrTerm2.getType() != null && equalityTermTail.getType() == null)
      return andOrTerm2.getType();
    return null;
  }
}
