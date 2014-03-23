package symboltableconstruction;

import parser.Node;
import symboltable.*;

public class SymbolTableBuilder {
  private Node node;
  private Type variableType;
  private SymbolTable symbolTable;

  public SymbolTableBuilder() {
    symbolTable = new SymbolTable();
  }

  public SymbolTable getConstructedTable() {
    return symbolTable;
  }

  public void visit(Node node) {
    this.node = node;
    if (node.getTokenTypeOfChild().equals("<type-declaration>"))
      addTypeToTable();
    else if (node.getTokenTypeOfChild().equals("<var-declaration>"))
      handleVariableDeclaration();
    else if (node.getTokenTypeOfChild().equals("<funct-declaration>"))
      addFunctionToTable();
  }

  private void handleVariableDeclaration() {
    variableType = getTypeOfChildOfNode(node, 3, 0);
    VariableBuilder vb = new VariableBuilder(node, variableType);
    while (vb.hasMoreVariables())
      addNextVariable(vb.getNextVariable());
  }

  private void addNextVariable(Entry e) {
    if (e instanceof Array)
      symbolTable.addArray(e);
    else
      symbolTable.addVariable(e);
  }

  private void addFunctionToTable() {
    FunctionBuilder fb = new FunctionBuilder(node, symbolTable);
    symbolTable.addFunction(fb.getFunction());
  }

  private void addTypeToTable() {
    String typeName = (node.getTokenTypeOfChild(1));
    String actualType = "";
    for (int i = 0; i < node.children.get(3).children.size(); i++)
      actualType += getType(i);
    symbolTable.addType(new Type(typeName, actualType));
  }

  private String getType(int i) {
    if (node.getTokenTypeOfChild(3, i).equals("<type-id>"))
      return node.navigateToChild(3, i).getLastChild().getTokenTypeOfChild() + " ";
    else
      return node.getTokenTypeOfChild(3, i) + " ";
  }

  private Type getTypeOfChildOfNode(Node node, int... indices) {
    return (Type) symbolTable.getType(node.getTokenTypeOfChild(indices));
  }
}
