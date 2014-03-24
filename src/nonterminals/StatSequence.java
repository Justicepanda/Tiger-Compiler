package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

class StatSequence extends ParserRule {
  StatSequence(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {

  }

  @Override
  public String getLabel() {
    return "<stat-seq>";
  }
}
