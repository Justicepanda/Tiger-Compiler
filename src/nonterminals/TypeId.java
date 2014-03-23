package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

class TypeId extends ParserRule {
  private String type;

  TypeId(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    if (peekTypeMatches("INT")) {
      type = "int";
      matchTerminal("INT");
    }
    else if (peekTypeMatches("STRING")) {
      type = "string";
      matchTerminal("STRING");
    }
    else {
      type = symbolTable.getType(peekTokenValue()).getActualType();
      matchTerminal("ID");
    }
  }

  public String getType() {
    return type;
  }
}
