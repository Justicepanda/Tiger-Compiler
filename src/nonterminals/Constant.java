package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class Constant extends ParserRule {
  private String value;

  public Constant(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    value = peekTokenValue();
    if (peekTypeMatches("INTLIT"))
      matchTerminal("INTLIT");
    else if (peekTypeMatches("STRLIT"))
      matchTerminal("STRLIT");
    else
      matchTerminal("NIL");
  }

  @Override
  public String getLabel() {
    return "<const>";
  }

  public String getValue() {
    if (value == null)
      return "nil";
    else
      return value;
  }
}
