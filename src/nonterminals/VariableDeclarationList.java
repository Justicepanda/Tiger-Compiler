package nonterminals;

import parser.ParserRule;
import symboltable.Type;

class VariableDeclarationList extends ParserRule {

  private VariableDeclaration variableDeclaration;
  private VariableDeclarationList variableDeclarationList;

  @Override
  public void parse() {
    if (peekTypeMatches("VAR")) {
      variableDeclaration = new VariableDeclaration();
      variableDeclarationList = new VariableDeclarationList();
      matchNonTerminal(variableDeclaration);
      matchNonTerminal(variableDeclarationList);
    }
  }

  @Override
  public String getLabel() {
    return "<var-declaration-list>";
  }

  @Override
  public Type getType() {
    return null;
  }
}
