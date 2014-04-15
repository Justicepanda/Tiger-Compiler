package backend;

import java.util.ArrayList;
import java.util.List;

public class BasicBlock 
{
	private List<BasicBlock> children;
	private List<String> lines;
	
	public BasicBlock()
	{
		this.lines = new ArrayList<String>();
		this.children = new ArrayList<BasicBlock>();
	}
	
	public BasicBlock(List<String> lines)
	{
		this.lines = lines;
		this.children = new ArrayList<BasicBlock>();
	}
	
	public BasicBlock(List<BasicBlock> c, List<String> lines)
	{
		children = c;
		this.lines = lines;
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
				
				BasicBlock leftBlock = new BasicBlock();
				leftBlock.createGraph(leftLines);
				BasicBlock rightBlock = new BasicBlock();
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
