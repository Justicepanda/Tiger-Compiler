package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

class TypeDeclaration extends ParserRule {
  private Type type;

  TypeDeclaration(Scanner scanner) {
    super(scanner);
    type = new Type(scanner);
  }

  @Override
  public void parse() {
    matchTerminal("TYPE");
    String id = peekTokenValue();
    matchTerminal("ID");
    matchTerminal("EQ");
    matchNonTerminal(type);
    matchTerminal("SEMI");
    symbolTable.addType(new symboltable.Type(id, type.getType()));
  }
}
