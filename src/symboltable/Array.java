package symboltable;

import java.util.ArrayList;
import java.util.List;

public class Array extends Variable {
  private final Type type;
  private List<String> values;
  private List<Integer> dimensions;

  public Array(Type type, String name, List<Integer> dimensions) {
    super(type, name);
    this.type = type;
    initList(dimensions);
  }

  private void initList(List<Integer> dimensions) {
    this.dimensions = dimensions;
    int size = 1;
    for (Integer dimension : dimensions)
      size *= dimension;
    values = new ArrayList<String>();
    for (int i = 0; i < size; i++)
      values.add("nil");
  }

  public List<Integer> getDimensions() {
    return dimensions;
  }

  public void setValue(String value) {
    for (int i = 0; i < values.size(); i++)
      values.set(i, value);
  }

  @Override
  public String toString() {
    return "Array: " + getName() +
            ", Type: " + type.getName() +
            ", Scope: " + getScope() +
            ", Initial Value: " + values;
  }
}
