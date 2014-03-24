package parser;

import nonterminals.TigerProgram;
import scanner.Scanner;

public class TigerParser {
  private final Scanner scanner;

  public TigerParser(Scanner scanner) {
    this.scanner = scanner;
  }

  public void parse() {
    TigerProgram program = new TigerProgram(scanner);
    ParserRule.reset();
    program.parse();
    System.out.println(ParserRule.print());
  }
}