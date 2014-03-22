package symboltable;

import java.util.ArrayList;

public class FunctionDeclaration extends Entry {
  private final ArrayList<Argument> arguments;
  private final Type returnType;

  public FunctionDeclaration(String name, ArrayList<Argument> args, Type returnType) {
    super(name);
    this.arguments = args;
    this.returnType = returnType;
  }

  @Override
  public String toString() {
    return "Function: " + getName() +
            ", Return Type: " + returnType.getName() +
            ", Arguments: " + arguments +
            ", Scope: " + getScope();
  }
}

