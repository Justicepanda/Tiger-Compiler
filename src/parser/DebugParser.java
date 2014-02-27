package parser;

import frontend.TokenTuple;
import scanner.Scanner;

public class DebugParser extends Parser {
  public DebugParser(Scanner scanner, String tableFilename, String rulesFilename) {
    super(scanner, tableFilename, rulesFilename);
  }

  @Override
  protected void handleNextToken(TokenTuple token) {
    super.handleNextToken(token);
    System.out.print(token.getType() + " ");
  }
}
