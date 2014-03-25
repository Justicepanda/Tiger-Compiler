package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class AndOrTermTail extends ParserRule {
  private final EqualityTermTail equalityTermTail = new EqualityTermTail();
  private final AndOrTerm2 andOrTerm2 = new AndOrTerm2();
  private Type type;

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
    this.type = decideType(equalityTermTail, andOrTerm2);
    return decideType(equalityTermTail, andOrTerm2);
  }
}
