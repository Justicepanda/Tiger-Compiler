package nonterminals;

import parser.ParserRule;
import symboltable.Type;

class DeclarationSegment extends ParserRule {

  private final TypeDeclarationList typeDeclarationList = new TypeDeclarationList();
  private final VariableDeclarationList variableDeclarationList = new VariableDeclarationList();
  private final FunctionDeclarationList functionDeclarationList = new FunctionDeclarationList();

  @Override
  public void parse() {
    matchNonTerminal(typeDeclarationList);
    matchNonTerminal(variableDeclarationList);
    matchNonTerminal(functionDeclarationList);
  }

  @Override
  public String getLabel() {
    return "<declaration-segment>";
  }

  @Override
  public Type getType() {
    return null;
  }

  @Override
  public String generateCode() {
    typeDeclarationList.generateCode();
    variableDeclarationList.generateCode();
    functionDeclarationList.generateCode();
    return null;
  }
}
