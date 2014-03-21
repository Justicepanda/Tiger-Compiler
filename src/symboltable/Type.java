package symboltable;

public class Type extends Entry 
{
	private String name;
	private String actualType;
	
	public Type(String name, String actualType)
	{
		this.name = name;
		this.actualType = actualType;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getActualType()
	{
		return actualType;
	}
	
	public boolean equals(Object o)
	{
		if(o instanceof Type)
		{
			Type t = (Type)o;
			if(t.getName().equals(getName()))
			{
				return true;
			}
		}
		
		return false;
	}	
}
