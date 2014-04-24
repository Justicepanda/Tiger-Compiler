package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import symboltable.Entry;
import symboltable.SymbolTable;

public class BasicBlock 
{
	private List<BasicBlock> children;
	private List<String> lines;
	private int loopDepth;
	
	public BasicBlock(int loopDepth)
	{
		this.lines = new ArrayList<String>();
		this.children = new ArrayList<BasicBlock>();
		this.loopDepth = loopDepth;
	}
	
	public BasicBlock(List<String> lines, int loopDepth)
	{
		this.lines = lines;
		this.children = new ArrayList<BasicBlock>();
		this.loopDepth = loopDepth;
	}
	
	public BasicBlock(List<BasicBlock> c, List<String> lines, int loopDepth)
	{
		children = c;
		this.lines = lines;
		this.loopDepth = loopDepth;
	}
	
	public List<BasicBlock> getChildren()
	{
		return children;
	}
	
	public void setChildren(List<BasicBlock> c)
	{
		this.children = c;
	}
	
	public void addLine(String line)
	{
		this.lines.add(line);
	}
	
	public String print(int tabCount)
	{
		String tabs = "";
		for(int i = 0; i < tabCount; i++)
			tabs += "\t";
		String output = "\n\t<Node>";
		for(int i = 0; i < lines.size(); i++)
		{
			output += "\n" + tabs + lines.get(i);
		}
		
		if(children.size() > 0)
		{
			tabCount++;
			for(BasicBlock child: children)
			{
				output += child.print(tabCount);
			}
		}
		return output;
	}
	
	public HashMap<String, LiveRange> getLiveRanges()
	{
		HashMap<String, LiveRange> liveRanges = new HashMap<String, LiveRange>();
		for(int i = 0; i < lines.size(); i++)
		{
			String[] lineParams = lines.get(i).split(",");
			for(int j = 1; j < lineParams.length; j++)
			{
				String key = lineParams[j].replaceAll("\\s", "");	
				if(SymbolTable.getVariable(key) != null || SymbolTable.getTemporary(key) != null || SymbolTable.getArray(key) != null)
				{
					if(liveRanges.keySet().contains((key)))
					{
						//Find the LiveRange for this variable in the hashmap
						liveRanges.get(key).setEnd(i);
					}
					else
					{
						//This is the first time this variable has been seen
						liveRanges.put(key, new LiveRange(i));
					}
				}
			}
		}
		
		return liveRanges;
	}
	
	public HashMap<String, Integer> getSpillCosts()
	{
		HashMap<String, Integer> spillCosts = new HashMap<String, Integer>();
		for(int i = 0; i < lines.size(); i++)
		{
			String[] lineParams = lines.get(i).split(",");
			if(lineParams[0].replaceAll("\\s", "").contains("br"))
				loopDepth++;
			if(lineParams.length > 3 && lineParams[3].replaceAll("\\s", "").contains("endloop"))
				loopDepth--;
			for(int j = 1; j < lineParams.length; j++)
			{
				String key = "";
				if(spillCosts.keySet().contains((key = lineParams[j].replaceAll("\\s", ""))))
				{
					//Find the LiveRange for this variable in the hashmap
					Integer n = spillCosts.get(key);
					n = new Integer((int)(n.intValue() + (1 * Math.pow(10.0, (double)loopDepth))));
				}
				else
				{
					//This is the first time this variable has been seen
					spillCosts.put(key, new Integer((int)(1 * Math.pow(10.0, (double)loopDepth))));
				}
			}
		}
		
		return spillCosts;
	}
	
	public String allocateRegisters()
	{
		String output = "";
		InterferenceGraph graph = new InterferenceGraph();
		graph.buildGraph(getLiveRanges(), getSpillCosts());
		InterferenceGraph coloredGraph = new InterferenceGraph();
		Stack<InterferenceNode> coloringStack = new Stack<InterferenceNode>();
		//Run graph coloring algorithm using graph and stack
		boolean cannotBeRColored = true;
		while(cannotBeRColored)
		{
			InterferenceNode node;
			while((node = graph.hasRemovableNode()) != null)
			{
				coloringStack.push(node);
				graph.nodes.remove(node);
			}
			if(graph.nodes.size() == 0)
			{
				int curReg = 0;
				//Graph has been cleared, graph is 9-colorable
				while(coloringStack.size() > 0)
				{
					InterferenceNode n = coloringStack.pop();
					n.setRegister(curReg);
					
					//Set the register number in the symboltable
					Entry e;
					if((e = SymbolTable.getVariable(n.getName())) != null)
					{
						e.setRegister(curReg);
					}
					else if((e = SymbolTable.getArray(n.getName())) != null)
					{
						e.setRegister(curReg);
					}
					else if((e = SymbolTable.getTemporary(n.getName())) != null)
					{
						e.setRegister(curReg);
					}
					
					curReg++;
					coloredGraph.nodes.add(n);
				}
				
				cannotBeRColored = false;
			}
			else
			{
				InterferenceNode n = graph.getHighestSpillNode();
				graph.nodes.remove(n);
				n.setToSpill();
				coloredGraph.nodes.add(n);
			}
		}
		
		//Building the output string: loads, code, stores
		//Then call this on children and return output string
		for(int i = 0; i < coloredGraph.nodes.size(); i++)
		{
			output += "loadval, r" + coloredGraph.nodes.get(i).getRegister() + ", " + coloredGraph.nodes.get(i).getName() + "\n";
		}
		for(int i = 0; i < lines.size(); i++)
		{
			output += lines.get(i) + "\n";
		}
		for(int i = 0; i < coloredGraph.nodes.size(); i++)
		{
			output += "store, r" + coloredGraph.nodes.get(i).getRegister() + ", " + coloredGraph.nodes.get(i).getName() + "\n";
		}
		
		if(children.size() > 0)
		{
			if(children.get(1) != null)	
				output += children.get(1).allocateRegisters();
			if(children.get(0) != null)
				output += children.get(0).allocateRegisters();
		}
		
		return output;
	}
	
	public void createGraph(List<String> lines)
	{
		boolean done = false;
		int i = 0;
		while(!done)
		{
			this.addLine(lines.get(i));
			if(lines.get(i).split(",")[0].equals("breq") || lines.get(i).split(",")[0].equals("brneq") || lines.get(i).split(",")[0].equals("brlt") 
					|| lines.get(i).split(",")[0].equals("brgt") || lines.get(i).split(",")[0].equals("brgeq") || lines.get(i).split(",")[0].equals("brleq"))
			{
				//Add the code in the left branch to the leftBrach block
				ArrayList<String> leftLines = new ArrayList<String>();
				int searchInd = i + 1;
				while(!lines.get(searchInd).contains(lines.get(i).split(",")[3].replace(" ", "")))
				{
					leftLines.add(lines.get(searchInd));
					searchInd++;
				}
				
				ArrayList<String> rightLines = new ArrayList<String>();
				//Add the code in the left branch to the leftBrach block
				for(int j = searchInd; j < lines.size(); j++)
				{
					rightLines.add(lines.get(j));
				}
				
				BasicBlock leftBlock = new BasicBlock(loopDepth);
				leftBlock.createGraph(leftLines);
				BasicBlock rightBlock = new BasicBlock(loopDepth);
				rightBlock.createGraph(rightLines);
				
				this.children.add(leftBlock);
				this.children.add(rightBlock);

				done = true;
			}
			
			i++;
			
			if(i >= lines.size())
			{
				done = true;
			}
		}
	}
}
