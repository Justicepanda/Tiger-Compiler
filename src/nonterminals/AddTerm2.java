package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;

public class AddTerm2 extends ParserRule {
  private MultTerm multTerm;
  private AddTerm2 addTerm2;
  private int lineNumber;
  private AddOp addOp;

  @Override
  public void parse() {
    if (peekTypeMatches("PLUS") || peekTypeMatches("MINUS")) {
      lineNumber = getLineNumber();
      addOp = new AddOp();
      multTerm = new MultTerm();
      addTerm2 = new AddTerm2();
      matchNonTerminal(addOp);
      matchNonTerminal(multTerm);
      matchNonTerminal(addTerm2);
    }
  }

  @Override
  public String getLabel() {
    return "<add-term2>";
  }

  public Type getType() {
    if (multTerm == null)
      return null;

    if (addTerm2 != null && addTerm2.getType() != null && multTerm.getType() != null) {
      if (multTerm.getType().isOfSameType(addTerm2.getType()))
        return multTerm.getType();
      throw new SemanticTypeException(lineNumber);
    } else if (multTerm.getType() != null && (addTerm2 == null || addTerm2.getType() == null))
      return multTerm.getType();
    else if (addTerm2 != null && addTerm2.getType() != null)
      return addTerm2.getType();
    return null;
  }
}
