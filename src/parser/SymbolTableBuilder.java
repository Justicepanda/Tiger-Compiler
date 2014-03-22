package parser;

import symboltable.*;

import java.util.ArrayList;

public class SymbolTableBuilder {
  private Node node;
  private Type variableType;
  private SymbolTable symbolTable;

  public SymbolTableBuilder() {
    symbolTable = new SymbolTable();
  }

  public SymbolTable constructTable(Node node) {
    for (int i = 0; i < node.children.size(); i++) {
      if (node.children.size() > 0) {
        visit(node.children.get(i));
        constructTable(node.children.get(i));
      }
    }
    return symbolTable;
  }

  public void visit(Node node) {
    this.node = node;
    if (node.token.getToken().equals("<type-declaration>"))
      addTypeToTable();
    else if (node.token.getToken().equals("<var-declaration>"))
      handleVariableDeclaration();
    else if (node.token.getToken().equals("<funct-declaration>"))
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
    String functionName = node.getTokenTypeOfChild(1);
    Type type = getType();
    ArrayList<Argument> arguments = getArguments();
    FunctionDeclaration fd = new FunctionDeclaration(functionName, arguments, type);
    symbolTable.addFunction(fd);
  }

  private ArrayList<Argument> getArguments() {
    ArrayList<Argument> arguments = new ArrayList<Argument>();
    if (node.childHasChildren(3)) {
      Node param = node.navigateToChild(3, 0);
      do {
        Type argType = getTypeOfChildOfNode(param, 2, 0);
        arguments.add(new Argument(argType, param.getTokenTypeOfChild(0)));
        param = param.children.get(1);
      } while (param.hasChildren());
    }
    return arguments;
  }

  private Type getType() {
    Type type;
    if (node.childHasChildren(5))
      type = getTypeOfChildOfNode(node, 5, 1, 0);
    else
      type = new Type(null, null);
    return type;
  }

  private void addTypeToTable() {
    String typeName = (node.children.get(1)).token.getToken();
    String actualType = "";

    //The children of <type>
    for (int i = 0; i < node.children.get(3).children.size(); i++) {
      //This is the right value of a type assignment
      if (node.children.get(3).children.get(i).token.getToken().equals("<type-id>"))
        actualType += node.children.get(3).children.get(i).getLastChild().token.getToken() + " ";
      else
        actualType += node.children.get(3).children.get(i).token.getToken() + " ";
    }

    symbolTable.addType(new Type(typeName, actualType));
  }

  private Type getTypeOfChildOfNode(Node node, int... indices) {
    return (Type) symbolTable.getType(node.getTokenTypeOfChild(indices));
  }
}
