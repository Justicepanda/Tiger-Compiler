package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;

public class EqualityTerm2 extends ParserRule {
  private AddTerm addTerm;
  private EqualityTerm2 equalityTerm2;
  private int lineNumber;
  private Equality equality;

  @Override
  public void parse() {
    if (isEqualityTerm()) {
      lineNumber = getLineNumber();
      equality = new Equality();
      addTerm = new AddTerm();
      equalityTerm2 = new EqualityTerm2();
      matchNonTerminal(equality);
      matchNonTerminal(addTerm);
      matchNonTerminal(equalityTerm2);
    }
  }

  private boolean isEqualityTerm() {
    return peekTypeMatches("NEQ") || peekTypeMatches("EQ")
            || peekTypeMatches("LESSER") || peekTypeMatches("GREATER")
            || peekTypeMatches("LESSEREQ") || peekTypeMatches("GREATEREQ");
  }

  @Override
  public String getLabel() {
    return "<ineq-term-2>";
  }

  @Override
  public Type getType() {
    if (equalityTerm2 != null && equalityTerm2.getType() != null && addTerm != null && equalityTerm2.getType() != null) {
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
