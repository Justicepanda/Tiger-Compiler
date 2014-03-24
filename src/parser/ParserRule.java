package parser;

import compiler.TokenTuple;
import scanner.Scanner;
import symboltable.SymbolTable;

public abstract class ParserRule {
  protected Scanner scanner;
  protected static SymbolTable symbolTable = new SymbolTable();
  private static SimpleTree tree = new SimpleTree();

  public ParserRule(Scanner scanner) {
    this.scanner = scanner;
  }

  public static void reset() {
    symbolTable = new SymbolTable();
    tree = new SimpleTree();
  }

  protected void matchTerminal(String expected) {
    tree.add(expected);
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
    tree.add(expected.getLabel());
    tree.moveDown();
    expected.parse();
    tree.moveUp();
  }

  public abstract void parse();

  public abstract String getLabel();

  public static String print() {
    return tree.print() + symbolTable.print();
  }
}
