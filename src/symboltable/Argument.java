package symboltable;

public class Argument extends Entry {
  private final Type type;

  public Argument(Type type, String name) {
    super(name);
    this.type = type;
  }

  @Override
  public String toString() {
    return type.getName() + ": " + getName();
  }
}
