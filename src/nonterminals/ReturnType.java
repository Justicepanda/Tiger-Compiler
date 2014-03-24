package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

public class ReturnType extends ParserRule {

  private TypeId typeId;

  public ReturnType(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    if (peekTypeMatches("COLON")) {
      matchTerminal("COLON");
      typeId = new TypeId(scanner);
      matchNonTerminal(typeId);
    }
  }

  @Override
  public String getLabel() {
    return "<ret-type>";
  }

  public Type getType() {
    return typeId.getType();
  }
}
