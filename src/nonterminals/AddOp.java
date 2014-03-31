package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class AddOp extends ParserRule {
  @Override
  public void parse() {
    if (peekTypeMatches("PLUS"))
      matchTerminal("PLUS");
    else
      matchTerminal("MINUS");
  }

  @Override
  public String getLabel() {
    return "<add-op>";
  }

  @Override
  public Type getType() {
    return null;
  }

  @Override
  public String generateCode() {
    return null;
  }
}
