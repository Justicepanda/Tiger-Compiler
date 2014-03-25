package symboltable;

public class Variable extends Entry {
  private final Type type;
  private String value;

  public Variable(Type type, String name) {
    super(name);
    this.type = type;
    value = "nil";
  }

  public Type getType() {
    return type;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "Variable: " + getName() +
            ", Type: " + type.getName() +
            ", Scope: " + getScope() +
            ", Initial Value: " + value;
  }
}
