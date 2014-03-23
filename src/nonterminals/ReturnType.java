package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class ReturnType extends ParserRule {
  public ReturnType(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    if (peekTypeMatches("INT") || peekTypeMatches("STRING") || peekTypeMatches("ID")) {
      matchNonTerminal(new TypeId(scanner));
    }
  }
}
