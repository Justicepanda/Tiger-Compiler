package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class MultTerm extends ParserRule {
  private final Factor factor = new Factor();
  private final MultTerm2 multTerm2 = new MultTerm2();

  @Override
  public void parse() {
    storeLineNumber();
    matchNonTerminal(factor);
    matchNonTerminal(multTerm2);
  }

  @Override
  public String getLabel() {
    return "<mult-term>";
  }

  @Override
  public Type getType() {
    return decideType(factor, multTerm2);
  }
}
