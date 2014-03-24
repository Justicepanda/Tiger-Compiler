package symboltable;

public class Argument extends Entry 
{
	private Type type;

	public Argument(Type type, String name)
	{
		super(name);
		this.type = type;
	}

	@Override
	public String toString() 
	{
		if (type == null)
			type = new Type("nil", "nil");
		return type.getName() + ": " + getName();
	}

	public Type getType()
	{
		return type;
	}
}
