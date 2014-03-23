package semantics;

import compiler.TokenTuple;
import parser.Rule;
import symboltable.SymbolTable;
import symboltable.Type;

import java.util.ArrayList;
import java.util.List;

public class SemanticRule {
  private List<TokenTuple> tokens;
  private List<TokenTuple> expectedTokens;
  private Type type;

  public SemanticRule(Rule rule) {
    tokens = new ArrayList<TokenTuple>();
    expectedTokens = new ArrayList<TokenTuple>();
    for (int i = 0; i < rule.getLength(); i++)
      expectedTokens.add(rule.getToken(i));
  }

  public void acceptToken(TokenTuple token) {
    tokens.add(token);
    if (token.getToken().equals("INTLIT") && type == null)
      type = new Type("int", "int");
    else if (token.getToken().equals("INTLIT") && !type.getActualType().equals("int"))
      System.out.println("Error");
      // TODO

    if (token.getToken().equals("STRLIT") && type == null)
      type = new Type("string", "string");
    else if (token.getToken().equals("STRLIT") && !type.getActualType().equals("string"))
      System.out.println("Error");

    if (token.getType().equals("ID")) {
      Type tokenType = SymbolTable.getType(token.getToken());
      if (type == null)
        type = tokenType;
      if (!tokenType.isOfSameType(type))
        System.out.println("Error");
    }
  }

  public void checkChildRule(SemanticRule rule) {
    if (type == null)
      type = rule.type;
    if (!rule.type.isOfSameType(type))
      System.out.println("Error");
  }
}
