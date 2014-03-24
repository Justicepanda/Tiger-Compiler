package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

class VariableDeclarationList extends ParserRule {
  VariableDeclarationList(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    if (peekTypeMatches("VAR")) {
      matchNonTerminal(new VariableDeclaration(scanner));
      matchNonTerminal(new VariableDeclarationList(scanner));
    }
  }

  @Override
  public String getLabel() {
    return "<var-declaration-list>";
  }
}
