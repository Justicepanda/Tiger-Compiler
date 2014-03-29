package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class MultTerm2 extends ParserRule {
  private Factor factor;
  private MultTerm2 multTerm2;
  private MultOp multOp;


  @Override
  public void parse() {
    if (peekTypeMatches("MULT") || peekTypeMatches("DIV"))
      matchMultOp();
  }

  private void matchMultOp() {
    multOp = new MultOp();
    factor = new Factor();
    multTerm2 = new MultTerm2();
    storeLineNumber();
    matchNonTerminal(multOp);
    matchNonTerminal(factor);
    matchNonTerminal(multTerm2);
  }

  @Override
  public String getLabel() {
    return "<mult-term2>";
  }

  @Override
  public Type getType() {
    return decideType(factor, multTerm2);
  }
}
