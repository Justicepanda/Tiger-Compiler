package symboltableconstruction;

import parser.Node;
import symboltable.Argument;
import symboltable.Function;
import symboltable.SymbolTable;
import symboltable.Type;

import java.util.ArrayList;
import java.util.List;

public class FunctionBuilder {
  private Node node;
  private SymbolTable table;

  public FunctionBuilder(Node node, SymbolTable table) {
    this.node = node;
    this.table = table;
  }

  public Function getFunction() {
    String name = node.getTokenTypeOfChild(1);
    Type type = getType();
    List<Argument> arguments = getArguments();
    return new Function(name, arguments, type);
  }

  private Type getType() {
    Type type;
    if (node.childHasChildren(5))
      type = getTypeOfChildOfNode(5, 1, 0);
    else
      type = new Type(null, null);
    return type;
  }

  private List<Argument> getArguments() {
    if (node.childHasChildren(3))
      return getValidArguments();
    else
      return new ArrayList<Argument>();
  }

  private List<Argument> getValidArguments() {
    ArrayList<Argument> arguments = new ArrayList<Argument>();
    node = node.navigateToChild(3, 0);
    do arguments.add(getNextArgument());
    while (node.hasChildren());
    return arguments;
  }

  private Argument getNextArgument() {
    Type argType = getTypeOfChildOfNode(2, 0);
    Argument argument = new Argument(argType, node.getTokenTypeOfChild(0));
    node = node.children.get(1);
    return argument;
  }

  private Type getTypeOfChildOfNode(int... indices) {
    return (Type) table.getType(node.getTokenTypeOfChild(indices));
  }
}
