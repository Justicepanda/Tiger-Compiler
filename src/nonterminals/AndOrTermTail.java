package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;

public class AndOrTermTail extends ParserRule {
  private final EqualityTermTail equalityTermTail = new EqualityTermTail();
  private final AndOrTerm2 andOrTerm2 = new AndOrTerm2();

  @Override
  public void parse() {
    storeLineNumber();
    matchNonTerminal(equalityTermTail);
    matchNonTerminal(andOrTerm2);
  }

  @Override
  public String getLabel() {
    return "<and-or-term-tail>";
  }

  @Override
  public Type getType() {
    if (andOrTerm2.getType() != null && equalityTermTail.getType() != null) {
      if (equalityTermTail.getType().isOfSameType(andOrTerm2.getType()))
        return equalityTermTail.getType();
      generateException();
    } else if (equalityTermTail.getType() != null)
      return equalityTermTail.getType();
    else if (andOrTerm2.getType() != null)
      return andOrTerm2.getType();
    return null;
  }
}
