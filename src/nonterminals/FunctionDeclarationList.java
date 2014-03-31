package nonterminals;

import parser.ParserRule;
import symboltable.Type;

class FunctionDeclarationList extends ParserRule {

  private FunctionDeclaration functionDeclaration;
  private FunctionDeclarationList functionDeclarationList;

  @Override
  public void parse() {
    if (peekTypeMatches("FUNC"))
      matchFunction();
  }

  private void matchFunction() {
    functionDeclaration = new FunctionDeclaration();
    functionDeclarationList = new FunctionDeclarationList();
    matchNonTerminal(functionDeclaration);
    matchNonTerminal(functionDeclarationList);
  }

  @Override
  public String getLabel() {
    return "<funct-declaration-list>";
  }

  @Override
  public Type getType() {
    return null;
  }

  @Override
  public String generateCode() {
    if (functionDeclaration != null) {
      functionDeclaration.generateCode();
      functionDeclarationList.generateCode();
    }
    return null;
  }
}
