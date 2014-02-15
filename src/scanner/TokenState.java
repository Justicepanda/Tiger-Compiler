package scanner;

import dfa.State;

import java.util.HashMap;
import java.util.Map;

public class TokenState implements State {
  private final String text;
  private final boolean isAcceptState;
  private final Map<String, Integer> destinations;

  private int defaultDestination;
  private int otherPunctuationDestination;
  private int spaceDestination;
  private int numDestination;

  public TokenState(String text, boolean isAcceptState) {
    this.text = text;
    this.isAcceptState = isAcceptState;
    destinations = new HashMap<String, Integer>();

    this.defaultDestination = Integer.MAX_VALUE;
    otherPunctuationDestination = Integer.MAX_VALUE;
    spaceDestination = Integer.MAX_VALUE;
    numDestination = Integer.MAX_VALUE;
  }

  public boolean isAcceptState() {
    return isAcceptState;
  }

  public String getName() {
    return text;
  }

  public int getDestination(String input) {
    if (destinations.containsKey(input)) return destinations.get(input);
    else if (isOtherPunctuation(input)) return otherPunctuationDestination;
    else if (isSpace(input)) return spaceDestination;
    else if (isNum(input)) return numDestination;
    else return defaultDestination;
  }

  public void addDestination(String tag, int destination) {
    if (tag.equals("DEFAULT")) setDefaultDestination(destination);
    else if (tag.equals("~`!@#$%?")) otherPunctuationDestination = destination;
    else if (tag.equals("SPACE")) spaceDestination = destination;
    else if (tag.equals("0-9")) numDestination = destination;
    else if (tag.equals("comma")) destinations.put(",", destination);
    else destinations.put(tag, destination);
  }

  private void setDefaultDestination(int destination) {
    defaultDestination = destination;
    if (otherPunctuationDestination == Integer.MAX_VALUE) otherPunctuationDestination = destination;
    if (spaceDestination == Integer.MAX_VALUE) spaceDestination = destination;
    if (numDestination == Integer.MAX_VALUE) numDestination = destination;
  }

  private boolean isOtherPunctuation(String str) {
    char ch = str.charAt(0);
    return ch == '~' || ch == '`' || ch == '!' || ch == '@' || ch == '#' || ch == '$' || ch == '%' || ch == '?';
  }

  private boolean isSpace(String str) {
    char ch = str.charAt(0);
    return ch == ' ' || ch == '\t' || ch == '\n';
  }

  private boolean isNum(String str) {
    char ch = str.charAt(0);
    return ch >= '0' && ch <= '9';
  }
}
