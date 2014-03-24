package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

class TypeDeclarationList extends ParserRule {
  TypeDeclarationList(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    if (peekTypeMatches("TYPE")) {
      matchNonTerminal(new TypeDeclaration(scanner));
      matchNonTerminal(new TypeDeclarationList(scanner));
    }
  }

  @Override
  public String getLabel() {
    return "<type-declaration-list>";
  }
}
