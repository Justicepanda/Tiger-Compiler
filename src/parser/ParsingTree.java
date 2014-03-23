package parser;

import compiler.TokenTuple;
import semantics.SemanticRule;
import symboltable.SymbolTable;
import symboltableconstruction.SymbolTableBuilder;

import java.util.Stack;

public class ParsingTree {
  private final Stack<Rule> rules;
  private final Node parentNode;
  private Node currentNode;
  private SymbolTableBuilder builder;
  private Stack<SemanticRule> semanticRules;

  ParsingTree() {
    currentNode = new Node(new TokenTuple("EXIT", "$"), null);
    parentNode = currentNode;
    rules = new Stack<Rule>();
    rules.push(Rule.determineFrom("let <declaration-segment> in <stat-seq> end"));
    builder = new SymbolTableBuilder();
    semanticRules = new Stack<SemanticRule>();
  }

  public SymbolTable getSymbolTable() {
    return builder.getConstructedTable();
  }

  String print() {
    return parentNode.print("");
  }

  void addNonTerminal(TokenTuple nonTerminalToken, Rule nonTerminalRule) {
    if (!semanticRules.isEmpty())
      semanticRules.peek().acceptToken(nonTerminalToken);
    semanticRules.push(new SemanticRule(nonTerminalRule));
    moveAndAdd(nonTerminalToken);
    currentNode = currentNode.getLastChild();

    if (currentNode == null)
      System.out.println("CurrentNode is now null");

    rules.push(nonTerminalRule.copy());
    handleFinishedRules();
  }

  void addTerminal(TokenTuple terminalToken) {
    semanticRules.peek().acceptToken(terminalToken);
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
      SemanticRule top = semanticRules.pop();
      semanticRules.peek().checkChildRule(top);
      builder.visit(currentNode);
      currentNode = currentNode.getParent();
    }
  }
}

