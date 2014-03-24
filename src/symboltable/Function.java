package symboltable;

import java.util.ArrayList;
import java.util.List;

public class Function extends Entry {
  private final List<Argument> arguments;
  private Type returnType;

  public Function(String name, List<Argument> arguments, Type returnType) {
    super(name);
    this.arguments = arguments;
    this.returnType = returnType;
  }

  @Override
  public String toString() {
    if (returnType == null)
      returnType = new Type("nil", "nil");
    return "Function: " + getName() +
            ", Return Type: " + returnType.getName() +
            ", Arguments: " + arguments +
            ", Scope: " + getScope();
  }
  
  public Type getReturnType()
  {
	  return returnType;
  }
  
  public ArrayList<Argument> getArguments()
  {
	  return (ArrayList)arguments;
  }
}

