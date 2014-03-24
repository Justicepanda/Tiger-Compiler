package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Variable;

public class VariableDeclaration extends ParserRule {
  private IdList idList;
  private TypeId typeId;
  private OptionalInit optionalInit;

  public VariableDeclaration(Scanner scanner) {
    super(scanner);
    idList = new IdList(scanner);
    typeId = new TypeId(scanner);
    optionalInit = new OptionalInit(scanner);
  }

  @Override
  public void parse() {
    matchTerminal("VAR");
    matchNonTerminal(idList);
    matchTerminal("COLON");
    matchNonTerminal(typeId);
    matchNonTerminal(optionalInit);
    matchTerminal("SEMI");

    for (String id: idList.getIds()) {
      Variable var = new Variable(typeId.getType(), id);
      symbolTable.addVariable(var);
      var.setValue(optionalInit.getValue());
    }

  }

  @Override
  public String getLabel() {
    return "<var-declaration>";
  }
}
