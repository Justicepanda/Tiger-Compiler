package symboltable;

import java.util.ArrayList;
import java.util.List;

public class Array extends Entry {
  private final Type type;
  private final List<Integer> dimensions;
  private List<String> values;

  public Array(Type type, String name, List<Integer> dimensions) {
    super(name);
    this.type = type;
    this.dimensions = dimensions;
    initList(dimensions);
  }

  private void initList(List<Integer> dimensions) {
    int size = 1;
    for (Integer dimension : dimensions)
      size *= dimension;
    values = new ArrayList<String>();
    for (int i = 0; i < size; i++)
      values.add(null);
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
            ", CurrentValue: " + values;
  }

  //Unused
  /*
  public void setValue(ArrayList<Integer> indices, String value) {
    int offset = 0;
    for (int i = 0; i < dimensions.size(); i++)
      if (i < dimensions.size() - 1)
        offset += indices.get(i) * getSubspaceSize(i, indices);
      else
        offset += indices.get(i);
    values.set(offset, value);
  }
  */
  /*
  public String getValue(ArrayList<Integer> indices) {
    int offset = 0;
    for (int i = 0; i < dimensions.size(); i++)
      if (i < dimensions.size() - 1)
        offset += indices.get(i) * getSubspaceSize(i, indices);
      else
        offset += indices.get(i);
    return values.get(offset);
  }
  private int getSubspaceSize(int i, ArrayList<Integer> indices) {
    int subspaceSize = 0;
    for (int j = i + 1; j < dimensions.size(); j++)
      subspaceSize += indices.get(j) * dimensions.get(j);
    return subspaceSize;
  }
  */
}
