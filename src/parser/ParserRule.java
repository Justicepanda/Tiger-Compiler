package parser;

import compiler.TokenTuple;
import scanner.Scanner;
import symboltable.SymbolTable;

public abstract class ParserRule {
  protected Scanner scanner;
  protected static SymbolTable symbolTable = new SymbolTable();

  public ParserRule(Scanner scanner) {
    this.scanner = scanner;
  }

  protected void matchTerminal(String expected) {
    TokenTuple actual = scanner.getNextToken();
    if (!actual.getType().equals(expected))
      throw new TerminalException(actual, new TokenTuple(expected, expected));
    System.out.print(actual.getType() + " ");
  }

  protected boolean peekTypeMatches(String toMatch) {
    return scanner.peek().getType().equals(toMatch);
  }

  protected String peekTokenValue() {
    return scanner.peek().getToken();
  }

  protected void matchNonTerminal(ParserRule expected) {
    expected.parse();
  }

  public abstract void parse();
}
