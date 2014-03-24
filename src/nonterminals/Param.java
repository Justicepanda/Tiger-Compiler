package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Argument;

public class Param extends ParserRule {

  private TypeId typeId;
  private String id;

  public Param(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    id = peekTokenValue();
    matchTerminal("ID");
    matchTerminal("COLON");
    typeId = new TypeId(scanner);
    matchNonTerminal(typeId);
  }

  @Override
  public String getLabel() {
    return "<param>";
  }

  public Argument getArgument() {
    return new Argument(typeId.getType(), id);
  }
}
