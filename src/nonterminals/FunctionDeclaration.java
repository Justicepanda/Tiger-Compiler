package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class FunctionDeclaration extends ParserRule {
  private final ParamList paramList = new ParamList();
  private final ReturnType returnType = new ReturnType();
  private final StatSequence statSequence  = new StatSequence();

  @Override
  public void parse() {
    storeLineNumber();
    String id = matchFunction();
    semanticCheck();
    addFunction(id, paramList.getArguments(), returnType.getType());
  }

  private String matchFunction() {
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
    return id;
  }

  private void semanticCheck() {
    if (noReturnFunctionWithReturnStatements())
      generateException();

    for (Expression returnExpr: statSequence.getReturnExpressions())
      if (!rulesMatchType(returnExpr, returnType))
        generateException();
  }

  private boolean noReturnFunctionWithReturnStatements() {
    return returnType.getType().isExactlyOfType(Type.NIL_TYPE) &&
            !statSequence.getReturnExpressions().isEmpty();
  }

  @Override
  public String getLabel() {
    return "<funct-declaration>";
  }

  @Override
  public Type getType() {
    return returnType.getType();
  }

  @Override
  protected String generateCode() {
    return null;
  }
}
