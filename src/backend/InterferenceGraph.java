package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InterferenceGraph 
{
	List<InterferenceNode> nodes;
	
	public InterferenceGraph()
	{
		this.nodes = new ArrayList<InterferenceNode>();
	}
	
	public void buildGraph(HashMap<String, LiveRange> liveRanges, HashMap<String, Integer> spillCosts)
	{
		for(String key: liveRanges.keySet())
		{	
			InterferenceNode node = new InterferenceNode(key);
			node.setSpillCost(spillCosts.get(key));
			nodes.add(node);
		}
		
		for(InterferenceNode node: nodes)
		{
			String key = node.getName();
			for(InterferenceNode otherNode: nodes)
			{
				String otherKey = otherNode.getName();
				if(!key.equals(otherKey))
				{
					//This is a potential neighbor, check it and if it is adjacent, add it as a neighbor to the newNode
					LiveRange x = liveRanges.get(key);
					LiveRange y = liveRanges.get(otherKey);
					if(!(x.getStart() < y.getStart() && x.getEnd() < y.getStart()) || !(x.getStart() > y.getEnd() && x.getEnd() > y.getEnd()))
					{
						//These two interfere, add it as a neighbor to this node
						node.addNeighbor(otherNode);
					}
				}
			}
		}
	}

	public InterferenceNode hasRemovableNode()
	{
		for(int i = 0; i < nodes.size(); i++)
		{
			if(nodes.get(i).getNeighbors().size() < 9)
			{
				return nodes.get(i);
			}
		}
		return null;
	}
	
	public InterferenceNode getHighestSpillNode()
	{
		InterferenceNode highestSpillNode = nodes.get(0);
		for(int i = 1; i < nodes.size(); i++)
		{
			if(highestSpillNode.getSpillCost() < nodes.get(i).getSpillCost())
			{
				highestSpillNode = nodes.get(i);
			}
		}
		
		return highestSpillNode;
	}
}
