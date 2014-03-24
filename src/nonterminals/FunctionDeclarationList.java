package nonterminals;

import parser.ParserRule;
import symboltable.Type;

class FunctionDeclarationList extends ParserRule {

  private FunctionDeclaration functionDeclaration;
  private FunctionDeclarationList functionDeclarationList;

  @Override
  public void parse() {
    if (peekTypeMatches("FUNC")) {
      functionDeclaration = new FunctionDeclaration();
      functionDeclarationList = new FunctionDeclarationList();
      matchNonTerminal(functionDeclaration);
      matchNonTerminal(functionDeclarationList);
    }
  }

  @Override
  public String getLabel() {
    return "<funct-declaration-list>";
  }

  @Override
  public Type getType() {
    return null;
  }
}
