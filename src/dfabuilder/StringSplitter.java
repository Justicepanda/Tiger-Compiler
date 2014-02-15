package dfabuilder;

import java.util.ArrayList;
import java.util.List;

public class StringSplitter {
  private final char splitAbout;
  private String toSplit;
  private int currChar;

  public StringSplitter(char splitAbout) {
    this.splitAbout = splitAbout;
  }

  public String[] split(String toSplit) {
    this.toSplit = toSplit;
    List<String> splitList = splitToList();
    String[] toReturn = new String[splitList.size()];
    return splitList.toArray(toReturn);
  }

  private List<String> splitToList() {
    List<String> split = new ArrayList<String>();
    for (currChar = 0; currChar < toSplit.length(); currChar++)
      if (toSplit.charAt(currChar) == splitAbout)
        return nextIteration(split);
    split.add(toSplit);
    return split;
  }

  private List<String> nextIteration(List<String> split) {
    split.add(toSplit.substring(0, currChar));
    toSplit = toSplit.substring(currChar+1, toSplit.length());
    split.addAll(splitToList());
    return split;
  }
}
