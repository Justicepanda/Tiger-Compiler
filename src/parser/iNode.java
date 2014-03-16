package parser;

import java.util.List;
import java.util.ArrayList;

import compiler.TokenTuple;
import symboltable.Type;
import parser.NonTermNode;
import parser.ExprNode;

public class iNode 
{
	//Need to add location data so errors are accurate
    protected iNode parent;
    protected List<iNode> children;
	
    public iNode()
    {
    	children = new ArrayList<iNode>();
    }
    
	public iNode getLastChild()
	{
      return children.get(children.size()-1);
    }

    public iNode getParent() 
    {
      return parent;
    }

    public void addChild(TokenTuple data) 
    {
    	if(isNonTerminal(data))
    		children.add(new NonTermNode(data, this));
    	else
    		children.add(new ExprNode(data.getType(), new Type(data.getToken()), this));
    }
    
    private boolean isNonTerminal(TokenTuple data)
    {
    	if(data.getToken().substring(0, 1).equals("<") && data.getToken().substring(data.getToken().length() - 2, data.getToken().length() - 1).equals(">"))
    	{
    		return true;
    	}
    	
    	return false;
    }
}
