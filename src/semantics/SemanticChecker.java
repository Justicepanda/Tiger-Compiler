package semantics;

import parser.Node;
import parser.ParsingTree;

import java.util.Stack;

public class SemanticChecker 
{
	private ParsingTree parsingTree;
	private Stack<Node> semanticStack;
  private int expressionScope;
  private boolean isInsideExpr;
	
	public SemanticChecker(ParsingTree parsingTree) {
		this.parsingTree = parsingTree;
		this.semanticStack = new Stack<Node>();
    isInsideExpr = false;
	}
	
	private void traverseTree(Node node) {
    increaseExpressionScope(node);
    if (isInsideExpr)
      handleNode(node);
    decreaseExpressionScope(node);
  }

  private void increaseExpressionScope(Node node) {
    if (node.isExpression()) {
      expressionScope++;
      isInsideExpr = true;
    }
  }

  private void handleNode(Node node) {
    for(int i = 0; i < node.children.size(); i++)
      traverseTree(node.navigateToChild(i));
    if (node.isTerminal())
      semanticStack.push(node);
  }

  private void decreaseExpressionScope(Node node) {
    if (node.isExpression()) {
      expressionScope--;
      if (expressionScope == 0)
        isInsideExpr = false;
    }
  }
}