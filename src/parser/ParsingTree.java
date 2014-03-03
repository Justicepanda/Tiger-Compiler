package parser;

import compiler.TokenTuple;

import java.util.ArrayList;
import java.util.List;

public class ParsingTree {
  private Node parentNode;
  private Node currentNode;

  ParsingTree() {
    currentNode = new Node(new TokenTuple("EXIT", "$"));
    parentNode = currentNode;
  }

  void moveDown() {
    currentNode = currentNode.getLastChild();
  }

  void moveUp() {
    currentNode = currentNode.getParent();
  }

  void addChild(TokenTuple data) {
    currentNode.addChild(data);
  }

  public String print() {
    return parentNode.print("");
  }

  private class Node {
    TokenTuple data;
    Node parent;
    List<Node> children;

    Node (TokenTuple data, Node parent) {
      this.data = data;
      this.parent = parent;
      children = new ArrayList<Node>();
    }

    Node(TokenTuple data) {
      this(data, null);
    }

    Node getLastChild() {
      return children.get(children.size()-1);
    }

    Node getParent() {
      return parent;
    }

    void addChild(TokenTuple data) {
      children.add(new Node(data, this));
    }

    public String print(String prepend) {
      String res = "";
      res += prepend + data.getToken() + "\n";
      for (Node n: children)
        res += n.print(prepend + "\t");
      return res;
    }
  }
}

