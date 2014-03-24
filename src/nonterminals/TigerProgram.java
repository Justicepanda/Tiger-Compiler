package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class TigerProgram extends ParserRule {
  public TigerProgram(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    matchTerminal("LET");
    matchNonTerminal(new DeclarationSegment(scanner));
    matchTerminal("IN");
    matchNonTerminal(new StatSequence(scanner));
    matchTerminal("END");
  }

  @Override
  public String getLabel() {
    return "<tiger-program>";
  }
}
