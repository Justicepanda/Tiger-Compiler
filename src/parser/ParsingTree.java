package parser;

import compiler.TokenTuple;

import java.util.Stack;

import symboltable.*;

class ParsingTree 
{
  private final Stack<Rule> rules;
  private final Node parentNode;
  private Node currentNode;

  ParsingTree() 
  {
	currentNode = new Node(new TokenTuple("EXIT", "$"), null);
    parentNode = currentNode;
    rules = new Stack<Rule>();
    rules.push(Rule.determineFrom("let <declaration-segment> in <stat-seq> end"));
  }

  String print() 
  {
    return ((Node)parentNode).print("");
  }

  void addNonTerminal(TokenTuple nonTerminalToken, Rule nonTerminalRule) 
  {
    moveAndAdd(nonTerminalToken);
    currentNode = currentNode.getLastChild();
    
    if(currentNode == null)
    	System.out.println("CurrentNode is now null");
    	
    rules.push(nonTerminalRule.copy());
    handleFinishedRules();
  }

  void addTerminal(TokenTuple terminalToken) 
  {
    moveAndAdd(terminalToken);
    handleFinishedRules();
  }

  private void moveAndAdd(TokenTuple pop) 
  {
    currentNode.addChild(pop);
    rules.peek().moveToNextToken();
  }

  private void handleFinishedRules() 
  {
    while (rules.peek().isFinished()) 
    {
      rules.pop();
      currentNode = currentNode.getParent();
    }
  }
  
  public Node getRoot()
  {
	  return parentNode;
  }
}

