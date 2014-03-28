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
    matchTerminal("FUNC");
    String id = matchIdAndGetValue();
    matchTerminal("LPAREN");
    matchNonTerminal(paramList);
    matchTerminal("RPAREN");
    matchNonTerminal(returnType);
    matchTerminal("BEGIN");
    matchNonTerminal(statSequence);

    semanticCheck();

    matchTerminal("END");
    matchTerminal("SEMI");

    addFunction(id, paramList.getArguments(), returnType.getType());
  }

  private void semanticCheck() {
    if (returnType.getType().isExactlyOfType(Type.NIL_TYPE) && !statSequence.getReturnExpressions().isEmpty())
      generateException();

    for (Expression returnExpr: statSequence.getReturnExpressions())
      if (!rulesMatchType(returnExpr, returnType))
        generateException();
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
