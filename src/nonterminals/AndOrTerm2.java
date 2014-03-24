package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;

public class AndOrTerm2 extends ParserRule {
  private AndOrOp andOrOp;
  private EqualityTerm equalityTerm;
  private AndOrTerm2 andOrTerm2;
  private int lineNumber;

  @Override
  public void parse() {
    if (peekTypeMatches("AND") || peekTypeMatches("OR")) {
      andOrOp = new AndOrOp();
      equalityTerm = new EqualityTerm();
      andOrTerm2 = new AndOrTerm2();
      lineNumber = getLineNumber();
      matchNonTerminal(andOrOp);
      matchNonTerminal(equalityTerm);
      matchNonTerminal(andOrTerm2);
    }
  }

  @Override
  public String getLabel() {
    return "<and-or-term2>";
  }

  @Override
  public Type getType() {
    if (andOrTerm2 != null && andOrTerm2.getType() != null && equalityTerm != null && equalityTerm.getType() != null) {
      if (equalityTerm.getType().isOfSameType(andOrTerm2.getType()))
        return equalityTerm.getType();
      throw new SemanticTypeException(lineNumber);
    } else if (equalityTerm != null && equalityTerm.getType() != null && andOrTerm2.getType() == null)
      return equalityTerm.getType();
    else if (andOrTerm2 != null && andOrTerm2.getType() != null && equalityTerm.getType() == null)
      return andOrTerm2.getType();

    return null;
  }
}
