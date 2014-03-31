package parser;

import java.util.HashMap;
import java.util.Map;

public class NameMaker {
  private int tempNum = 1;
  private Map<String, Integer> labelNums = new HashMap<String, Integer>();

  public String newTemp() {
    return "t" + tempNum++;
  }

  public String newLabel(String labelBase) {
    if (!labelNums.containsKey(labelBase))
      labelNums.put(labelBase, 0);
    int i = labelNums.get(labelBase);
    i++;
    labelNums.put(labelBase, i);
    return labelBase + i;
  }
}
