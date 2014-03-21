package symboltable;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class SymbolTable {
  private ArrayList<Type> types;
  private Map<String, ArrayList<Variable>> variables;
  private Map<String, ArrayList<FunctionDeclaration>> funcDecs;
  private int scope;

  public SymbolTable() {
    types = new ArrayList<Type>();
    variables = new HashMap<String, ArrayList<Variable>>();
    funcDecs = new HashMap<String, ArrayList<FunctionDeclaration>>();
    scope = 1;
    addType(new Type("int", "int"));
    addType(new Type("string", "string"));
  }

  public void moveUpScope() {
    scope++;
  }

  public void moveDownScope() {
    scope--;
  }

  public Type getType(String id) 
  {
	for(int i = 0; i < types.size(); i++)
    {
    	if(types.get(i).getName().equals(id))
    	{
    		return types.get(i);
    	}
    }
    
    return null;
  }
  
  public Variable getVariable(String id) 
  {
	    return variables.get(id).get(scope);
  }
  
  public FunctionDeclaration getFunction(String id) 
  {
	    return funcDecs.get(id).get(scope);
  }

  public void addType(Type entry) 
  {
	  entry.setScope(scope);
	  types.add(entry);
  }
  
  public void addVariable(String id, Variable entry) 
  {
    entry.setScope(scope);
    if (!variables.containsKey(id))
      variables.put(id, new ArrayList<Variable>());
    variables.get(id).add(entry);
  }
  
  public void print()
  {
	  System.out.println("--Symbol Table--");
	  for(Type e: types)
	  {
		  System.out.println("Type: " + e.getName() + ", Scope: " + e.getScope() + ", ActualType: " + e.getActualType());
	  }
	  for(ArrayList<Variable> varList: variables.values())
	  {
		  for(int i = 0; i < varList.size(); i++)
		  {
			  System.out.println("Variable: " + varList.get(i).getName() + ", Type: " + varList.get(i).getType().getName() + ", Scope: " + varList.get(i).getScope() + ", CurrentValue: " + varList.get(i).getValue());
		  }
	  }
	  
  }
}
