package symboltableconstruction;

import parser.Node;
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
    return node.getTokenTypeOfChild(4, 1, 0);
  }

  private String getNegativeValue() {
    return node.getTokenTypeOfChild(4, 1, 1);
  }

  public boolean hasMoreVariables() {
    return node.hasChildren();
  }

  public Entry getNextVariable() {
    Entry entry = getEntry();
    node = node.navigateToChild(nextChildLocation);
    setNextLocations();
    return entry;
  }

  private Entry getEntry() {
    Entry entry;
    if (isArray)
      entry = setupArray();
    else
      entry = setupVariable();
    return entry;
  }

  private Entry setupArray() {
    Array arr = new Array(type, node.getTokenTypeOfChild(nodeTypeLocation), dimensions);
    arr.setValue(value);
    return arr;
  }

  private Entry setupVariable() {
    Variable var = new Variable(type, node.getTokenTypeOfChild(nodeTypeLocation));
    var.setValue(value);
    return var;
  }

  private void setNextLocations() {
    nodeTypeLocation = 1;
    nextChildLocation = 2;
  }
}
