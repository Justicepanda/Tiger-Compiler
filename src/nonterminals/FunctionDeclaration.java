package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.*;
import symboltable.Type;

public class FunctionDeclaration extends ParserRule {
  private ParamList paramList;
  private ReturnType returnType;

  public FunctionDeclaration(Scanner scanner) {
    super(scanner);
    paramList = new ParamList(scanner);
    returnType = new ReturnType(scanner);
  }

  @Override
  public void parse() {
    matchTerminal("FUNC");
    String id = peekTokenValue();
    matchTerminal("ID");
    matchTerminal("LPAREN");
    matchNonTerminal(paramList);
    matchTerminal("RPAREN");
    matchNonTerminal(returnType);
    matchTerminal("BEGIN");
    matchNonTerminal(new StatSequence(scanner));
    matchTerminal("END");
    matchTerminal("SEMI");

    symbolTable.addFunction(new Function(id, paramList.getArguments(), returnType.getType()));
  }

  @Override
  public String getLabel() {
    return "<funct-declaration>";
  }

  @Override
  public Type getType() {
    return null;
  }
}
