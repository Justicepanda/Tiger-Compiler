package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class Param extends ParserRule {
  public Param(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    matchTerminal("ID");
    matchTerminal("COLON");
    matchNonTerminal(new TypeId(scanner));
  }
}
