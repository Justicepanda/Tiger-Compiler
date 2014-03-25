package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class AddTerm2 extends ParserRule {
  private MultTerm multTerm;
  private AddTerm2 addTerm2;
  private AddOp addOp;
  private Type type;

  @Override
  public void parse() {
    if (peekTypeMatches("PLUS") || peekTypeMatches("MINUS")) {
      addOp = new AddOp();
      multTerm = new MultTerm();
      addTerm2 = new AddTerm2();
      storeLineNumber();
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
    this.type = decideType(multTerm, addTerm2);
    return decideType(multTerm, addTerm2);
  }
}
