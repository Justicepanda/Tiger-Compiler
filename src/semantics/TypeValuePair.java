package semantics;

import symboltable.Type;

public class TypeValuePair
{
	private Type type;
	private String value;
	
	public TypeValuePair(Type type, String value)
	{
		this.type = type;
		this.value = value;
	}
	
	public void setType(Type type)
	{
		this.type = type;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}
	
	public Type getType()
	{
		return type;
	}
	
	public String getValue()
	{
		return value;
	}
}
