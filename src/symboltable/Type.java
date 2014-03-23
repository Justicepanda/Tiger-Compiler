package symboltable;

public class Type extends Entry {
  private final String actualType;

  public Type(String name, String actualType) {
    super(name);
    this.actualType = actualType;
  }

  public String getActualType() {
    return actualType;
  }

  public boolean equals(Object o) {
    if (!(o instanceof Type))
      return false;
    Type t = (Type) o;
    return t.getName().equals(getName());
  }

  public boolean isOfSameType(Type t) {
    return t.actualType.equals(actualType);
  }

  @Override
  public String toString() {
    return "Type: " + getName() +
            ", Scope: " + getScope() +
            ", ActualType: " + actualType;
  }
}
