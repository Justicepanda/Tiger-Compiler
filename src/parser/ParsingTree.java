package parser;

import compiler.TokenTuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class ParsingTree {
  private final Stack<Rule> rules;
  private final Node parentNode;
  private Node currentNode;

  ParsingTree() {
    currentNode = new Node(new TokenTuple("EXIT", "$"));
    parentNode = currentNode;
    rules = new Stack<Rule>();
    rules.push(Rule.determineFrom("let <declaration-segment> in <stat-seq> end"));
  }

  String print() {
    return parentNode.print("");
  }

  void addNonTerminal(TokenTuple nonTerminalToken, Rule nonTerminalRule) {
    moveAndAdd(nonTerminalToken);
    currentNode = currentNode.getLastChild();
    rules.push(nonTerminalRule.copy());
    handleFinishedRules();
  }

  void addTerminal(TokenTuple terminalToken) {
    moveAndAdd(terminalToken);
    handleFinishedRules();
  }

  private void moveAndAdd(TokenTuple pop) {
    currentNode.addChild(pop);
    rules.peek().moveToNextToken();
  }

  private void handleFinishedRules() {
    while (rules.peek().isFinished()) {
      rules.pop();
      currentNode = currentNode.getParent();
    }
  }

  private class Node {
    final TokenTuple data;
    final Node parent;
    final List<Node> children;

    private Node (TokenTuple data, Node parent) {
      this.data = data;
      this.parent = parent;
      children = new ArrayList<Node>();
    }

    private Node(TokenTuple data) {
      this(data, null);
    }

    private Node getLastChild() {
      return children.get(children.size()-1);
    }

    private Node getParent() {
      return parent;
    }

    private void addChild(TokenTuple data) {
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

