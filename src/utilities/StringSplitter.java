package utilities;

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
    currChar = -1;
    while (currChar < toSplit.length()-1) {
      currChar++;
      if (toSplit.charAt(currChar) == splitAbout)
        return nextIteration(split);
      else if (isEscapedValue(splitAbout) || isEscapedValue('\\'))
        removeCurrChar();
    }
    split.add(toSplit);
    return split;
  }

  boolean isEscapedValue(char value) {
    return toSplit.charAt(currChar) == '\\' && toSplit.charAt(currChar+1) == value;
  }

  private void removeCurrChar() {
    toSplit = toSplit.substring(0, currChar) + toSplit.substring(currChar+1);
  }

  private List<String> nextIteration(List<String> split) {
    split.add(toSplit.substring(0, currChar));
    toSplit = toSplit.substring(currChar + 1, toSplit.length());
    split.addAll(splitToList());
    return split;
  }
}
