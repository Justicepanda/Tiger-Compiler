package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;

public class AddTerm extends ParserRule {
  private int lineNumber;
  private final MultTerm multTerm = new MultTerm();
  private final AddTerm2 addTerm2 = new AddTerm2();

  @Override
  public void parse() {
    lineNumber = super.getLineNumber();
    matchNonTerminal(multTerm);
    matchNonTerminal(addTerm2);
  }

  @Override
  public String getLabel() {
    return "<add-term>";
  }

  @Override
  public Type getType() {
    if (addTerm2.getType() != null && multTerm.getType() != null) {
      if (multTerm.getType().isOfSameType(addTerm2.getType()))
        return multTerm.getType();
      throw new SemanticTypeException(lineNumber);
    } else if (multTerm.getType() != null)
      return multTerm.getType();
    else if (addTerm2.getType() != null)
      return addTerm2.getType();
    return null;
  }
}
