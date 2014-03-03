package symboltable;

public abstract class Entry {
  private int scope;

  public int getScope() {
    return scope;
  }

  void setScope(int scope) {
    this.scope = scope;
  }
}
