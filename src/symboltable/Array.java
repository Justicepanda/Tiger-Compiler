package symboltable;

import java.util.ArrayList;

public class Array extends Entry
{
	private Type type;
	private String name;
	private ArrayList<Integer> dimensions;
	private ArrayList<String> values;
	
	public Array(Type type, String name, ArrayList<Integer> dimensions)
	{
		this.type = type;
		this.name = name;
		this.dimensions = dimensions;
		int size = 1;
		for(int i = 0; i < dimensions.size(); i++)
		{
			size *= dimensions.get(i);
		}
		this.values = new ArrayList<String>();
		
		for(int i = 0; i < size; i++)
		{
			this.values.add(null);
		}
	}
	
	public String getValue(ArrayList<Integer> indices)
	{
		int offset = 0;
		for(int i = 0; i < dimensions.size(); i++)
		{
			if(i < dimensions.size() - 1)
				offset += indices.get(i) * getSubspaceSize(i, indices);
			else
				offset += indices.get(i);
		}
		return values.get(offset);
	}
	
	public void setValue(String value)
	{
		for(int i = 0; i < values.size(); i++)
		{
			values.set(i, value);
		}
	}
	
	public void setValue(ArrayList<Integer> indices, String value)
	{
		int offset = 0;
		for(int i = 0; i < dimensions.size(); i++)
		{
			if(i < dimensions.size() - 1)
				offset += indices.get(i) * getSubspaceSize(i, indices);
			else
				offset += indices.get(i);
		}
		values.set(offset, value);
	}
	
	private int getSubspaceSize(int i, ArrayList<Integer> indices)
	{
		int subspacesize = 0;
		for(int j = i + 1; j < dimensions.size(); j++)
		{
			subspacesize += indices.get(j) * dimensions.get(j);
		}
		
		return subspacesize;
	}
	
	public Type getType()
	{
		return type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<Integer> getDimensions()
	{
		return dimensions;
	}
	
	public ArrayList<String> getList()
	{
		return values;
	}
}
