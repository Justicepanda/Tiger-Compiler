package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class MultOp extends ParserRule {

  @Override
  public void parse() {
    if (peekTypeMatches("MULT"))
      matchTerminal("MULT");
    else
      matchTerminal("DIV");
  }

  @Override
  public String getLabel() {
    return "<mult-op>";
  }

  @Override
  public Type getType() {
    return null;
  }

  @Override
  protected String generateCode() {
    return null;
  }

}
