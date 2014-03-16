package parser;

import compiler.TokenTuple;
import java.util.Stack;

class ParsingTree 
{
  private final Stack<Rule> rules;
  private final iNode parentNode;
  private iNode currentNode;

  ParsingTree() {
	currentNode = new NonTermNode(new TokenTuple("EXIT", "$"), null);
    parentNode = currentNode;
    rules = new Stack<Rule>();
    rules.push(Rule.determineFrom("let <declaration-segment> in <stat-seq> end"));
  }

  String print() 
  {
    return ((NonTermNode)parentNode).print("");
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
}

