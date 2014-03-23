package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class FunctionDeclaration extends ParserRule {
  public FunctionDeclaration(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    matchTerminal("FUNCTION");
    matchTerminal("ID");
    matchTerminal("LPAREN");
    matchNonTerminal(new ParamList(scanner));
    matchTerminal("RPAREN");
    matchNonTerminal(new ReturnType(scanner));
    matchTerminal("BEGIN");
    matchNonTerminal(new StatSequence(scanner));
    matchTerminal("END");
    matchTerminal("SEMI");
  }
}
