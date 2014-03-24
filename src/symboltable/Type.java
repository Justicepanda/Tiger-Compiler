package symboltable;

public class Type extends Entry {
  private final String actualType;

  public static final Type STRING_TYPE = new Type("string", "string");
  public static final Type INT_TYPE = new Type("int", "int");
  public static final Type NIL_TYPE = new Type("nil", "nil");

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
    if (t == null)
      return true;
    if (t.actualType.equals("nil") || actualType.equals("nil"))
      return true;
    return actualType.equals(t.actualType);
  }

  @Override
  public String toString() {
    return "Type: " + getName() +
            ", Scope: " + getScope() +
            ", ActualType: " + actualType;
  }
}
