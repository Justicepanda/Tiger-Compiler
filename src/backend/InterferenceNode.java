package backend;

import java.util.ArrayList;
import java.util.List;

public class InterferenceNode 
{
	private List<InterferenceNode> neighbors;
	private String name;
	private int register;
	private int spillCost = 0;
	private boolean isSpilled = false;
	
	public InterferenceNode(String name)
	{
		this.name = name;
		this.neighbors = new ArrayList<InterferenceNode>();
	}
	
	public void addNeighbor(InterferenceNode node)
	{
		this.neighbors.add(node);
	}
	
	public String getName()
	{
		return name;
	}

	public List<InterferenceNode> getNeighbors() 
	{
		return neighbors;
	}
	
	public void setRegister(int reg)
	{
		register = reg;
	}
	
	public int getRegister()
	{
		return register;
	}
	
	public void setToSpill()
	{
		isSpilled = true;
	}
	
	public boolean isSpilled()
	{
		return isSpilled;
	}
	
	public int getSpillCost()
	{
		return spillCost;
	}
	
	public void setSpillCost(int cost)
	{
		spillCost = cost;
	}
}
