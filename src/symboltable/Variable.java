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
  
  public String getName()
  {
	  return name;
  }
  
  public Type getType()
  {
	  return type;
  }
  
  public void setValue(String value)
  {
	  this.value = value;
  }
  
  public String getValue()
  {
	  return value;
  }
}
