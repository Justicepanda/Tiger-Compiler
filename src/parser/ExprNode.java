package parser;

import java.util.ArrayList;

import symboltable.SymbolTable;
import symboltable.Type;

public class ExprNode extends iNode
{
    final String name;
    final Type type;
    ArrayList<Error> errors;
    SymbolTable symTab;

    public ExprNode(String name, Type type, iNode parent) {
      super();
      this.name = name;
      this.type = type;
      this.parent = parent;
      errors = new ArrayList<Error>();
      symTab = new SymbolTable();
    }

    public String print(String prepend) {
      String res = "";
      res += prepend + type.getName() + "\n";
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
