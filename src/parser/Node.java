package parser;

import java.util.List;
import java.util.ArrayList;

import semantics.TypeValuePair;
import compiler.TokenTuple;

public class Node {
  final TokenTuple token;
  private final Node parent;
  public final List<Node> children;
  private TypeValuePair typeValue;

  public Node(TokenTuple token, Node parent) {
    super();
    this.token = token;
    this.parent = parent;
    children = new ArrayList<Node>();
  }

  public Node getLastChild() {
    return children.get(children.size() - 1);
  }
  
  public TypeValuePair getTypeValuePair()
  {
	  return typeValue;
  }
  
  public void setTypeValuePair(TypeValuePair tp)
  {
	  this.typeValue = tp;
  }

  public Node getParent() {
    return parent;
  }

  public void addChild(TokenTuple data) {
	  Node newNode = new Node(data, this);
	  //newNode.setTypeValuePair(new TypeValuePair((Type)SymbolTable.getType(data.getType()), data.getToken()));
	  children.add(newNode);
  }

  public String print(String prepend) {
    String res = "";
    res += prepend + token.getToken() + "[" + token.getLocationInfo() + "]\n";
    for (Node n : children)
      res += n.print(prepend + "\t");
    return res;
  }

  public Node navigateToChild(int... indices) {
    Node res = this;
    for (int index: indices)
      res = res.children.get(index);
    return res;
  }

  public boolean childHasChildren(int... indices) {
    Node child = navigateToChild(indices);
    return child.hasChildren();
  }

  public boolean hasChildren() {
    return !children.isEmpty();
  }

  public String getTokenTypeOfChild(int... indices) {
    Node child = navigateToChild(indices);
    return child.token.getToken();
  }

  public boolean isTerminal() {
    return !(token.getType().equals("NONTERM"));
  }

  public boolean isExpression() {
    return token.getToken().equals("<expr>");
  }
}