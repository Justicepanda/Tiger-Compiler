package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class ParamListTail extends ParserRule {
  public ParamListTail(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    if (peekTypeMatches("COMMA")) {
      matchTerminal("COMMA");
      matchNonTerminal(new Param(scanner));
      matchNonTerminal(new ParamListTail(scanner));
    }
  }
}
