package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;

public class MultTerm2 extends ParserRule {
  private Factor factor;
  private MultTerm2 multTerm2;
  private MultOp multOp;


  @Override
  public void parse() {
    if (peekTypeMatches("MULT") || peekTypeMatches("DIV")) {
      multOp = new MultOp();
      factor = new Factor();
      multTerm2 = new MultTerm2();
      storeLineNumber();
      matchNonTerminal(multOp);
      matchNonTerminal(factor);
      matchNonTerminal(multTerm2);
    }
  }

  @Override
  public String getLabel() {
    return "<mult-term2>";
  }

  @Override
  public Type getType() {
    if (multTerm2 != null && multTerm2.getType() != null && factor != null && factor.getType() != null) {
      if (factor.getType().isOfSameType(multTerm2.getType()))
        return factor.getType();
      generateException();
    } else if (factor != null && factor.getType() != null)
      return factor.getType();
    else if (multTerm2 != null && multTerm2.getType() != null)
      return multTerm2.getType();
    return null;
  }
}
