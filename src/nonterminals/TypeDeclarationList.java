package nonterminals;

import parser.ParserRule;
import symboltable.Type;

class TypeDeclarationList extends ParserRule {
  private TypeDeclaration typeDeclaration;
  private TypeDeclarationList typeDeclarationList;

  @Override
  public void parse() {
    if (peekTypeMatches("TYPE")) {
      typeDeclaration = new TypeDeclaration();
      typeDeclarationList = new TypeDeclarationList();
      matchNonTerminal(typeDeclaration);
      matchNonTerminal(typeDeclarationList);
    }
  }

  @Override
  public String getLabel() {
    return "<type-declaration-list>";
  }

  @Override
  public Type getType() {
    return null;
  }
}
