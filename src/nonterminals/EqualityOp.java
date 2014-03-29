package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class EqualityOp extends ParserRule {
  @Override
  public void parse() {
    if (peekTypeMatches("NEQ"))
      matchTerminal("NEQ");
    else if (peekTypeMatches("EQ"))
      matchTerminal("EQ");
    else if (peekTypeMatches("LESSER"))
      matchTerminal("LESSER");
    else if (peekTypeMatches("GREATER"))
      matchTerminal("GREATER");
    else if (peekTypeMatches("LESSEREQ"))
      matchTerminal("LESSEREQ");
    else
      matchTerminal("GREATEREQ");
  }

  @Override
  public String getLabel() {
    return "<ineq>";
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
