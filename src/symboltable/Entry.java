package symboltable;

abstract class Entry {
  private int scope;
  private final String name;

  Entry(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  int getScope() {
    return scope;
  }

  void setScope(int scope) {
    this.scope = scope;
  }
}
