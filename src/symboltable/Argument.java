package symboltable;



public class Argument
{
	private final Type type;
	private final String name;
	
	public Argument(Type type, String name)
	{
		this.type = type;
		this.name = name;
	}
	
	public Type getType()
	{
		return type;
	}
	
	public String getName()
	{
		return name;
	}

  @Override
  public String toString() {
    return type + ": " + name;
  }
}
