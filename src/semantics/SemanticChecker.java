package semantics;

import parser.Node;
import parser.ParsingTree;

import java.util.Stack;

public class SemanticChecker 
{
	private ParsingTree parsingTree;
	private Stack<Node> semanticStack;
	
	public SemanticChecker(ParsingTree parsingTree)
	{
		this.parsingTree = parsingTree;
		this.semanticStack = new Stack<Node>();
	}
	
	private TypeValuePair traverseTree(Node node)
	{
		if(node.children.size() > 0)
		{
			for(int i = 0; i < node.children.size(); i++)
			{
				TypeValuePair childTVP = traverseTree(node.children.get(i));
				if(childTVP.getType().equals(node.getTypeValuePair().getType()))
				{
					
				}
			}
		}
		else
		{
			//This is a leaf node, pass data to parent
			semanticStack.add(node);
			return node.getTypeValuePair();
		}
	}
	
	private void visit(Node node)
	{
		
	}
}