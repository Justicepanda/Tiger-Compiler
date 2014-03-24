package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;

public class AddTerm extends ParserRule {
  private final MultTerm multTerm = new MultTerm();
  private final AddTerm2 addTerm2 = new AddTerm2();

  @Override
  public void parse() {
    storeLineNumber();
    matchNonTerminal(multTerm);
    matchNonTerminal(addTerm2);
  }

  @Override
  public String getLabel() {
    return "<add-term>";
  }

  @Override
  public Type getType() {
    /*
    if (rulesMatchType(multTerm, addTerm2))
      return multTerm.getType();
    else
      generateException();
    return null;
    */

    if (addTerm2.getType() != null && multTerm.getType() != null) {
      if (multTerm.getType().isOfSameType(addTerm2.getType()))
        return multTerm.getType();
      generateException();
    } else if (multTerm.getType() != null)
      return multTerm.getType();
    else if (addTerm2.getType() != null)
      return addTerm2.getType();
    return null;

  }
}
