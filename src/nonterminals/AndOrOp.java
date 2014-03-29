package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class AndOrOp extends ParserRule {
  @Override
  public void parse() {
    if (peekTypeMatches("OR"))
      matchTerminal("OR");
    else
      matchTerminal("AND");
  }

  @Override
  public String getLabel() {
    return "<and-or-op>";
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
