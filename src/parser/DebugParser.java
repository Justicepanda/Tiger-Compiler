package parser;

import frontend.TokenTuple;
import scanner.Scanner;

public class DebugParser extends Parser {
  public DebugParser(Scanner scanner, String tableFileName) {
    super(scanner, tableFileName);
  }

  @Override
  protected void handleNextToken(TokenTuple token) {
    super.handleNextToken(token);
    System.out.print(token.getType() + " ");
  }
}
