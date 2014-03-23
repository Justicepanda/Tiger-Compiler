package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class IdListTail extends ParserRule {
  public IdListTail(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    if (peekTypeMatches("COMMA")) {
      matchTerminal("COMMA");
      matchTerminal("ID");
      matchNonTerminal(new IdListTail(scanner));
    }
  }
}
