package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class MultTermTail extends ParserRule {
  private final LValue lValue = new LValue();
  private final MultTerm2 multTerm2 = new MultTerm2();

  @Override
  public void parse() {
    matchNonTerminal(lValue);
    matchNonTerminal(multTerm2);
  }

  @Override
  public String getLabel() {
    return "<mult-term-tail>";
  }

  @Override
  public Type getType() {
    return multTerm2.getType();
  }
}
