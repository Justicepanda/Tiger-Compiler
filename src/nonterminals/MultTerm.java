package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class MultTerm extends ParserRule {
  private final Factor factor = new Factor();
  private final MultTerm2 multTerm2 = new MultTerm2();
  private Type type;

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
    this.type = decideType(factor, multTerm2);
    return decideType(factor, multTerm2);
  }
}
