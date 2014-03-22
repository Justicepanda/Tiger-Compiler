package parser;

import java.util.List;
import java.util.ArrayList;

import compiler.TokenTuple;

public class Node {
  final TokenTuple token;
  private final Node parent;
  public final List<Node> children;

  public Node(TokenTuple token, Node parent) {
    super();
    this.token = token;
    this.parent = parent;
    children = new ArrayList<Node>();
  }

  public Node getLastChild() {
    return children.get(children.size() - 1);
  }

  public Node getParent() {
    return parent;
  }

  public void addChild(TokenTuple data) {
    children.add(new Node(data, this));
  }

  public String print(String prepend) {
    String res = "";
    res += prepend + token.getToken() + "\n";
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
}