package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;

public class AddTermTail extends ParserRule {
  private int lineNumber;
  private final MultTermTail multTermTail = new MultTermTail();
  private final AddTerm2 addTerm2 = new AddTerm2();

  @Override
  public void parse() {
    lineNumber = getLineNumber();
    matchNonTerminal(multTermTail);
    matchNonTerminal(addTerm2);
  }

  @Override
  public String getLabel() {
    return "<add-term-tail>";
  }

  @Override
  public Type getType() {
    if (addTerm2 != null && addTerm2.getType() != null && multTermTail != null && multTermTail.getType() != null) {
      if (multTermTail.getType().isOfSameType(addTerm2.getType()))
        return multTermTail.getType();
      throw new SemanticTypeException(lineNumber);
    } else if (multTermTail != null && multTermTail.getType() != null && addTerm2.getType() == null)
      return multTermTail.getType();
    else if (addTerm2 != null && addTerm2.getType() != null && multTermTail.getType() == null)
      return addTerm2.getType();
    return null;
  }
}
