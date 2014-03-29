package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class EqualityTerm2 extends ParserRule {
  private AddTerm addTerm;
  private EqualityTerm2 equalityTerm2;
  private EqualityOp equalityOp;
  private boolean wasExpanded;

  @Override
  public void parse() {
    if (isEqualityTerm())
      matchOperator();
  }

  private void matchOperator() {
    wasExpanded = true;
    storeLineNumber();
    equalityOp = new EqualityOp();
    addTerm = new AddTerm();
    equalityTerm2 = new EqualityTerm2();
    matchNonTerminal(equalityOp);
    matchNonTerminal(addTerm);
    matchNonTerminal(equalityTerm2);
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
    return decideType(addTerm, equalityTerm2);
  }

  public boolean wasExpanded() {
    return wasExpanded;
  }
}
