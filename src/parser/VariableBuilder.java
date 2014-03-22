package parser;

import symboltable.Array;
import symboltable.Entry;
import symboltable.Type;
import symboltable.Variable;

import java.util.ArrayList;
import java.util.List;

public class VariableBuilder {
  private Node node;
  private String value;
  private Type type;
  private boolean isArray;
  private List<Integer> dimensions;
  private int nodeTypeLocation = 0;
  private int nextChildLocation = 1;

  public VariableBuilder(Node node, Type type) {
    this.node = node;
    this.value = getValue();
    this.node = node.navigateToChild(1);
    this.type = type;
    isArray = determineIfArray();
    if (isArray)
      getDimensions();
  }

  private String getValue() {
    String value = "nil";
    if (node.childHasChildren(4))
      if (getLiteralValue().equals("-"))
        value = getLiteralValue() + getNegativeValue();
      else
        value = getLiteralValue();
    return value;
  }

  private boolean determineIfArray() {
    return type.getActualType().split(" ")[0].equals("array");
  }

  private void getDimensions() {
    dimensions = new ArrayList<Integer>();
    String[] temp = type.getActualType().split("\\[");
    for (int i = 1; i < temp.length; i++)
      dimensions.add(new Integer(temp[i].split(" ")[1]));
  }

  private String getLiteralValue() {
    return node.children.get(4).children.get(1).children.get(0).token.getToken();
  }

  private String getNegativeValue() {
    return node.children.get(4).children.get(1).children.get(1).token.getToken();
  }

  public boolean hasMoreVariables() {
    return node.hasChildren();
  }

  public Entry getNextVariable() {
    Entry entry;
    if (isArray)
      entry = setupArray();
    else
      entry = setupVariable();
    node = node.navigateToChild(nextChildLocation);
    setNextLocations();
    return entry;
  }

  private Entry setupArray() {
    Entry entry;
    Array arr = new Array(type, node.getTokenTypeOfChild(nodeTypeLocation), dimensions);
    arr.setValue(value);
    entry = arr;
    return entry;
  }

  private Entry setupVariable() {
    Entry entry;
    Variable var = new Variable(type, node.getTokenTypeOfChild(nodeTypeLocation));
    var.setValue(value);
    entry = var;
    return entry;
  }

  private void setNextLocations() {
    nodeTypeLocation = 1;
    nextChildLocation = 2;
  }
}
