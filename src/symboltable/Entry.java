package symboltable;

public abstract class Entry {
  private int scope;
  private final String name;
  private int register;

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
  
  public void setRegister(int reg)
  {
	  register = reg;
  }
  
  public int getRegister()
  {
	  return register;
  }
}
