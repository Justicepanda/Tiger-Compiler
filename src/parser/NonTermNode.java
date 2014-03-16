package parser;

import java.util.ArrayList;
import java.util.List;

import symboltable.SymbolTable;
import compiler.TokenTuple;

public class NonTermNode extends iNode
{
  final TokenTuple token;
  final iNode parent;
  
  ArrayList<Error> errorsIn;
  ArrayList<Error> errorsOut;
  SymbolTable symTab;

  public NonTermNode(TokenTuple token, iNode parent)
  {
	super();
    this.token = token;
    this.parent = parent;
    errorsIn = new ArrayList<Error>();
    errorsOut = new ArrayList<Error>();
    symTab = new SymbolTable();
  }
  
  public String print(String prepend) {
      String res = "";
      res += prepend + token.getType()+ "\n";
      for (iNode n: children)
      {
    	  if(n instanceof ExprNode)
    		  res += ((ExprNode)n).print(prepend + "\t");
    	  else
    		  res += ((NonTermNode)n).print(prepend + "\t");
      }
      	
      return res;
    }
}