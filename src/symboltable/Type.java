package symboltable;

public class Type extends Entry 
{
	private String name;
	
	public Type(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
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
