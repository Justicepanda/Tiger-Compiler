package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

public class OptionalInit extends ParserRule {
  private Constant constant;

  public OptionalInit(Scanner scanner) {
    super(scanner);
    constant = new Constant(scanner);
  }

  @Override
  public void parse() {
    if (peekTypeMatches("ASSIGN")) {
      matchTerminal("ASSIGN");

      matchNonTerminal(constant);
    }
  }

  @Override
  public String getLabel() {
    return "<optional-init>";
  }

  public String getValue() {
    return constant.getValue();
  }
}
