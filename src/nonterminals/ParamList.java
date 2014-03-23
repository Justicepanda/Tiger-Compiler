package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class ParamList extends ParserRule {
  public ParamList(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    if (peekTypeMatches("ID")) {
      matchNonTerminal(new Param(scanner));
      matchNonTerminal(new ParamListTail(scanner));
    }
  }
}
