package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

class Type extends ParserRule {
  private TypeId typeId;

  Type(Scanner scanner) {
    super(scanner);
    typeId = new TypeId(scanner);
  }

  @Override
  public void parse() {
    if (peekTypeMatches("ARRAY")) {
      matchTerminal("ARRAY");
      matchTerminal("LBRACK");
      matchTerminal("INTLIT");
      matchTerminal("RBRACK");
      matchTerminal("OF");
      matchNonTerminal(typeId);
    }
    else
      matchNonTerminal(typeId);
  }

  String getType() {
    return typeId.getType();
  }
}
