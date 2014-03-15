package symboltable;

public class Variable extends Entry 
{
  private final Type type;
  private final String name;
  private String value;

  public Variable(Type type, String name) 
  {
    this.type = type;
    this.name = name;
  }
  
  public void setValue(String value)
  {
	  value = value;
  }
  
  public String getValue()
  {
	  return value;
  }
}
