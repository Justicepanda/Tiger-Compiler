package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;

public class MultTerm extends ParserRule {
  private final Factor factor = new Factor();
  private final MultTerm2 multTerm2 = new MultTerm2();
  private int lineNumber;

  @Override
  public void parse() {
    lineNumber = getLineNumber();
    matchNonTerminal(factor);
    matchNonTerminal(multTerm2);
  }

  @Override
  public String getLabel() {
    return "<mult-term>";
  }

  @Override
  public Type getType() {
    if (multTerm2 != null && multTerm2.getType() != null && factor != null && factor.getType() != null) {
      if (factor.getType().isOfSameType(multTerm2.getType()))
        return factor.getType();
      throw new SemanticTypeException(lineNumber);
    } else if (factor != null && factor.getType() != null && multTerm2.getType() == null)
      return factor.getType();
    else if (multTerm2 != null && multTerm2.getType() != null && factor.getType() == null)
      return multTerm2.getType();
    return null;
  }
}
