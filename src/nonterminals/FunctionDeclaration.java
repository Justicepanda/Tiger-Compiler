package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Function;
import symboltable.Type;

public class FunctionDeclaration extends ParserRule {
  private final ParamList paramList = new ParamList();
  private final ReturnType returnType = new ReturnType();
  private final StatSequence statSequence  = new StatSequence();

  @Override
  public void parse() {
    matchTerminal("FUNC");
    String id = matchIdAndGetValue();
    matchTerminal("LPAREN");
    matchNonTerminal(paramList);
    matchTerminal("RPAREN");
    matchNonTerminal(returnType);
    matchTerminal("BEGIN");
    matchNonTerminal(statSequence);
    matchTerminal("END");
    matchTerminal("SEMI");

    addFunction(id, paramList.getArguments(), returnType.getType());
  }

  @Override
  public String getLabel() {
    return "<funct-declaration>";
  }

  @Override
  public Type getType() {
    return returnType.getType();
  }
}
