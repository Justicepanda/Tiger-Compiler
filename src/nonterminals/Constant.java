package nonterminals;

import parser.ParserRule;
import symboltable.Type;

import static symboltable.Type.INT_TYPE;
import static symboltable.Type.NIL_TYPE;
import static symboltable.Type.STRING_TYPE;

public class Constant extends ParserRule {
  private String value;
  private Type type;

  @Override
  public void parse() {
    value = peekTokenValue();
    if (peekTypeMatches("INTLIT"))
      matchInt();
    else if (peekTypeMatches("STRLIT"))
      matchString();
    else
      matchNil();
    type.setConstant(true);
  }

  private void matchInt() {
    type = INT_TYPE;
    matchTerminal("INTLIT");
  }

  private void matchString() {
    type = STRING_TYPE;
    matchTerminal("STRLIT");
  }

  private void matchNil() {
    type = NIL_TYPE;
    matchTerminal("NIL");
  }

  @Override
  public String getLabel() {
    return "<const>";
  }

  public String getValue() {
    return value;
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public String generateCode() {
    return value;
  }

  public int getIntegerValue() {
    if (type.isExactlyOfType(INT_TYPE))
      return Integer.valueOf(value);
    return 0;
  }
}
