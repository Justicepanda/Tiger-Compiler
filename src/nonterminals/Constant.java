package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class Constant extends ParserRule {
  public Constant(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    if (peekTypeMatches("INTLIT"))
      matchTerminal("INTLIT");
    else if (peekTypeMatches("STRLIT"))
      matchTerminal("STRLIT");
    else
      matchTerminal("NIL");
  }
}
