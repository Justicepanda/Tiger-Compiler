package backend;

import java.util.ArrayList;
import java.util.List;

public class ControlFlowGraph 
{
	private BasicBlock rootBlock;
	
	public ControlFlowGraph()
	{
		rootBlock = new BasicBlock(0);
	}
	
	public ControlFlowGraph(BasicBlock root)
	{
		rootBlock = root;
	}
	
	public BasicBlock getRoot()
	{
		return rootBlock;
	}
	
	public void setRoot(BasicBlock root)
	{
		rootBlock = root;
	}
	
	public void createGraph(List<String> lines)
	{
		rootBlock.createGraph(lines);
	}
	
	public String allocateRegisters()
	{
		return rootBlock.allocateRegisters();
	}
	
	public String print()
	{
		return rootBlock.print(2);
		
	}
}
