package symboltable;

import java.util.List;

public class Function extends Entry {
  private final List<Argument> arguments;
  private final Type returnType;

  public Function(String name, List<Argument> arguments, Type returnType) {
    super(name);
    this.arguments = arguments;
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

