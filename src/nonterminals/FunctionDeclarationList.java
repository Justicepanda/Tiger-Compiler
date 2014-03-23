package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

class FunctionDeclarationList extends ParserRule {
  FunctionDeclarationList(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    if (peekTypeMatches("FUNCTION")) {
      matchNonTerminal(new FunctionDeclaration(scanner));
      matchNonTerminal(new FunctionDeclarationList(scanner));
    }
  }
}
