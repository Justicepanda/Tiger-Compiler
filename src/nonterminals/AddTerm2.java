package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class AddTerm2 extends ParserRule {
  private MultTerm multTerm;
  private AddTerm2 addTerm2;
  private AddOp addOp;

  @Override
  public void parse() {
    if (peekTypeMatches("PLUS") || peekTypeMatches("MINUS"))
      matchOperator();
  }

  private void matchOperator() {
    addOp = new AddOp();
    multTerm = new MultTerm();
    addTerm2 = new AddTerm2();
    storeLineNumber();
    matchNonTerminal(addOp);
    matchNonTerminal(multTerm);
    matchNonTerminal(addTerm2);
  }

  @Override
  public String getLabel() {
    return "<add-term2>";
  }

  public Type getType() {
    return decideType(multTerm, addTerm2);
  }
}
