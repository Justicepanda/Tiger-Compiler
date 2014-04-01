package symboltable;

import java.util.List;

public class Type extends Entry {
  private String name;
  private final String actualType;
  private boolean isConstant;
  private List<Integer> dimensions;

  public static final Type STRING_TYPE = new Type("string", "string");
  public static final Type INT_TYPE = new Type("int", "int");
  public static final Type NIL_TYPE = new Type("nil", "nil");

  public Type(String name, String actualType) {
    super(name);
    this.name = name;
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
    if ((t.isConstant || isConstant) && !t.actualType.equals("nil") && !actualType.equals("nil")) {
      String tbaseType = t.actualType;
      String baseType = actualType;
      if (dimensions != null) {
        baseType = actualType.split("\\[")[0];
      }
      if (t.dimensions != null)
        tbaseType = t.actualType.split("\\[")[0];
      return tbaseType.equals(baseType);
    }
    return t.actualType.equals("nil") || actualType.equals("nil") || getName().equals(t.getName());
  }

  public boolean isExactlyOfType(Type t) {
    return t.getName().equals(getName());
  }

  public void setConstant(boolean isConstant) {
    this.isConstant = isConstant;
  }

  public void setAsArray(List<Integer> dimensions) {
    this.dimensions = dimensions;
  }

  public void setNotArray() {
    this.dimensions = null;
    this.name = actualType;
  }

  public boolean isArray() {
    return dimensions != null;
  }

  @Override
  public String toString() {
    return "Type: " + getName() +
            ", Scope: " + getScope() +
            ", ActualType: " + actualType;
  }

  public List<Integer> getDimensions() {
    return dimensions;
  }

  public boolean isConstant() {
    return isConstant;
  }

  public int getLinearSize() {
    int res = 1;
    for (int dim : dimensions)
      res *= dim;
    return res;
  }
}
