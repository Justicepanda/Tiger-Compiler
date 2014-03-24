package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
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
    if (multTerm2.getType() != null && factor.getType() != null) {
      if (factor.getType().isOfSameType(multTerm2.getType()))
        return factor.getType();
      generateException();
    } else if (factor.getType() != null)
      return factor.getType();
    else if (multTerm2.getType() != null)
      return multTerm2.getType();
    return null;
  }
}
