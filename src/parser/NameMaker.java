package parser;

public class NameMaker {
  private int tempNum = 1;

  public String newTemp() {
    return "t" + tempNum++;
  }
}
