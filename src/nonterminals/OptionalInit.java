package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class OptionalInit extends ParserRule {
  public OptionalInit(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    if (peekTypeMatches("ASSIGN")) {
      matchTerminal("ASSIGN");
      matchNonTerminal(new Constant(scanner));
    }
  }
}
