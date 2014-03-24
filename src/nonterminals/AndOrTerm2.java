package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class AndOrTerm2 extends ParserRule {
  private AndOrOp andOrOp;
  private EqualityTerm equalityTerm;
  private AndOrTerm2 andOrTerm2;

  @Override
  public void parse() {
    if (peekTypeMatches("AND") || peekTypeMatches("OR")) {
      andOrOp = new AndOrOp();
      equalityTerm = new EqualityTerm();
      andOrTerm2 = new AndOrTerm2();
      storeLineNumber();
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
    return decideType(equalityTerm, andOrTerm2);
  }
}
