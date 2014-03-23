package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class VariableDeclaration extends ParserRule {
  public VariableDeclaration(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    matchTerminal("VAR");
    matchNonTerminal(new IdList(scanner));
    matchTerminal("COLON");
    matchNonTerminal(new TypeId(scanner));
    matchNonTerminal(new OptionalInit(scanner));
  }
}
