package symboltable;

public class Variable extends Entry {
  private final String type;
  private final String name;

  public Variable(String type, String name) {
    this.type = type;
    this.name = name;
  }
}
