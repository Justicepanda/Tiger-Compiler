package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

class DeclarationSegment extends ParserRule {
  DeclarationSegment(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    matchNonTerminal(new TypeDeclarationList(scanner));
    matchNonTerminal(new VariableDeclarationList(scanner));
    matchNonTerminal(new FunctionDeclarationList(scanner));
  }

  @Override
  public String getLabel() {
    return "<declaration-segment>";
  }
}
