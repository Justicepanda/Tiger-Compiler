package backend;

import java.util.ArrayList;
import java.util.List;

import symboltable.Array;
import symboltable.SymbolTable;
import symboltable.Temporary;
import symboltable.Variable;

public class RegisterAllocator 
{
	private SymbolTable table;
	private String inputIrCode;
	private String outputIrCode;
	private DataModel memory;
	private ControlFlowGraph cfg;
	
	public RegisterAllocator(SymbolTable table, String irCode)
	{
		this.table = table;
		this.inputIrCode = irCode;
		this.memory = new DataModel(table.getVariables(), table.getFunctions(), table.getArrays(), table.getTemoraries());
		//This is where you can choose which type of register allocation to use
		this.outputIrCode = produceCFGCode();
	}
	
	public String getGeneratedIrCode()
	{
		return outputIrCode;
	}
	
	public String produceCFGCode()
	{
		String[] lines = inputIrCode.split("\n");
		
		//Build the control flow graph
		cfg = new ControlFlowGraph();
		List<String> lineList = new ArrayList<String>();
		//Put lines into a list
		for(int i = 0; i < lines.length; i++)
		{
			lineList.add(lines[i]);
		}
		
		cfg.createGraph(lineList);
		
		//Allocate registers intra-block using graph coloring
		lines = cfg.allocateRegisters().split("\n");
		ArrayList<String> newLines = new ArrayList<String>();
		for(int i = 0; i < lines.length; i++)
		{
			if(lines[i].contains(":"))
			{
				List<String> tempLines = generateCFGCode(lines[i].split(":")[1]);
				newLines.add(lines[i].split(":")[0] + ":");
				for(int j = 0; j < tempLines.size(); j++)
					newLines.add(tempLines.get(j));
			}
			else
			{
				List<String> tempLines = generateCFGCode(lines[i]);
				for(int j = 0; j < tempLines.size(); j++)
					newLines.add(tempLines.get(j));
			}
		}
		
		String output = "";
		for(int i = 0; i < newLines.size(); i++)
		{
			output += newLines.get(i);
		}
		
		return output;
	}
	
	public String produceNaiveCode()
	{
		String[] lines = inputIrCode.split("\n");
		ArrayList<String> newLines = new ArrayList<String>();
		for(int i = 0; i < lines.length; i++)
		{
			if(lines[i].contains(":"))
			{
				List<String> tempLines = generateNaiveCode(lines[i].split(":")[1]);
				newLines.add(lines[i].split(":")[0] + ":");
				for(int j = 0; j < tempLines.size(); j++)
					newLines.add(tempLines.get(j));
			}
			else
			{
				List<String> tempLines = generateNaiveCode(lines[i]);
				for(int j = 0; j < tempLines.size(); j++)
					newLines.add(tempLines.get(j));
			}
		}
		
		String output = "";
		for(int i = 0; i < newLines.size(); i++)
		{
			output += newLines.get(i);
		}
		
		return output;
	}
	
	private List<String> generateCFGCode(String string) 
	{
		List<String> newLines = new ArrayList<String>();
		//Parse the instruction to find the variables used
		String[] instrParams = string.split(",");
		if(isArrayStore(instrParams[0]))
		{
			newLines.add("loadaddr, s0, " + instrParams[1] + "\n"); //arr
			newLines.add("loadval, s1, " + instrParams[2] + "\n"); //0
			newLines.add("loadval, s2, " + instrParams[3] + "\n"); //a
			newLines.add(instrParams[0] + ", s0, s1, s2\n");
		}
		else if(isArrayLoad(instrParams[0]))
		{
			newLines.add("loadaddr, s0, " + instrParams[1] + "\n"); //a
			newLines.add("loadaddr, s1, " + instrParams[2] + "\n"); //arr
			newLines.add("loadval, s2, " + instrParams[3] + "\n"); //1
			newLines.add(instrParams[0] + ", s0, s1, s2\n");
		}
		else if(instrParams[0].replace("\\s", "").equals("store"))
		{
			newLines.add(string + "\n");
		}
		else if(instrParams[0].replace("\\s", "").equals("loadval") || instrParams[0].replace("\\s", "").equals("loadaddr"))
		{
			newLines.add(string + "\n");
		}
		else
		{
			if(instrParams[0].replace("\\s", "").equals("add") && isNumeric(instrParams[3]))
			{
				instrParams[0] = "addi";
			}
			
			if(instrParams[0].replace("\\s", "").equals("assign"))
			{
				newLines.add("loadval, s7, " + instrParams[2] + "\n");
				instrParams[2] = "s7";
			}
			
			String line = instrParams[0];
			for(int i = 1; i < instrParams.length; i++)
			{
				if(SymbolTable.getVariable(instrParams[i].replaceAll("\\s", "")) != null)
				{
					Variable v = SymbolTable.getVariable(instrParams[i].replaceAll("\\s", ""));
					line += ", r" + v.getRegister();
				}
				else if(SymbolTable.getArray(instrParams[i].replaceAll("\\s", "")) != null)
				{
					Array a = SymbolTable.getArray(instrParams[i].replaceAll("\\s", ""));
					line += ", r" + a.getRegister();
				}
				else if(SymbolTable.getTemporary(instrParams[i].replaceAll("\\s", "")) != null)
				{
					Temporary a = SymbolTable.getTemporary(instrParams[i].replaceAll("\\s", ""));
					line += ", r" + a.getRegister();
				}
				else
				{
					line += ", " + instrParams[i];
				}
			}
			newLines.add(line + "\n");
		}
		
		return newLines;
	}

	private List<String> generateNaiveCode(String string) 
	{
		List<String> newLines = new ArrayList<String>();
		//Parse the instruction to find the variables used
		String[] instrParams = string.split(",");
		if(isAssignment(instrParams[0]))
		{
			newLines.add("loadval, r0," + instrParams[1] + "\n");
			newLines.add("loadval, r1," + instrParams[2] + "\n");
			newLines.add("assign, r0, r1" + ",\n");
			newLines.add("store, r0," + instrParams[1] + "\n");
		}
		else if(isBinaryOp(instrParams[0]))
		{
			String r1 = "r1";
			String r2 = "r2";
			newLines.add("loadval, r0," + instrParams[1] + "\n");
			if(!isNumeric(instrParams[2]))
				newLines.add("loadval, r1, " + instrParams[2] + "\n");
			else
				r1 = instrParams[2];
			if(!isNumeric(instrParams[3]))
				newLines.add("loadval, r2," + instrParams[3] + "\n");
			else
				r2 = instrParams[3];
			newLines.add(instrParams[0] + ", r0, " + r1 + "," + r2 + "\n");
			newLines.add("store, r0, " + instrParams[1] + "\n");
		}
		else if(isBranch(instrParams[0]))
		{
			String r0 = "r0";
			String r1 = "r1";
			newLines.add("loadval, r0, " + instrParams[1] + "\n");
			newLines.add("loadval, r1, " + instrParams[2] + "\n");
			newLines.add(instrParams[0] + ", " + r0 + ", " + r1 + ", " + instrParams[3] + "\n");
		}
		else if(isArrayStore(instrParams[0]))
		{
			newLines.add("loadaddr, r0, " + instrParams[1] + "\n"); //arr
			newLines.add("loadval, r1, " + instrParams[2] + "\n"); //0
			newLines.add("loadval, r2, " + instrParams[3] + "\n"); //a
			newLines.add(instrParams[0] + ", r0, r1, r2\n");
		}
		else if(isArrayLoad(instrParams[0]))
		{
			newLines.add("loadaddr, r0, " + instrParams[1] + "\n"); //a
			newLines.add("loadaddr, r1, " + instrParams[2] + "\n"); //arr
			newLines.add("loadval, r2, " + instrParams[3] + "\n"); //1
			newLines.add(instrParams[0] + ", r0, r1, r2\n");
		}
		else if(isReturn(instrParams[0]))
		{
			//newLines.add("load, r0," + instrParams[1] + "\n");
			//newLines.add(instrParams[0] + ", r0" + "\n");
		}
		else if(isFunctionCall(instrParams[0]))
		{
			//EXTRA CREDIT
		}
		else if(isReturnFunctionCall(instrParams[0]))
		{
			//EXTRA CREDIT
		}
		else
			newLines.add(string  + "\n");
		
		return newLines;
	}
	
	private boolean isNumeric(String num)
	{
		try
		{
			Integer.parseInt(num.replaceAll("\\s", ""));
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	private boolean isReturnFunctionCall(String string) {
		if(string.equals("callr"))
			return true;
		else
			return false;
	}

	private boolean isFunctionCall(String string) {
		if(string.equals("call"))
			return true;
		else
			return false;
	}

	private boolean isReturn(String string) {
		if(string.equals("return"))
			return true;
		else
			return false;
	}

	private boolean isArrayLoad(String string) 
	{
		if(string.equals("array_load"))
			return true;
		else
			return false;
	}

	private boolean isArrayStore(String string) {
		if(string.equals("array_store"))
			return true;
		else
			return false;
	}

	private boolean isBranch(String string) 
	{
		if(string.equals("breq") || string.equals("brneq") || string.equals("brlt") || string.equals("brgt") || string.equals("brgeq") || string.equals("brleq"))
			return true;
		else
			return false;
	}

	private boolean isAssignment(String string) 
	{
		if(string.equals("assign"))
			return true;
		return false;
	}

	private boolean isBinaryOp(String string) 
	{
		if(string.equals("add") || string.equals("sub") || string.equals("mult") || string.equals("div") || string.equals("and") || string.equals("or"))
			return true;
		else
			return false;
	}
	
	public DataModel getDataModel()
	{
		return memory;
	}
	
	public String printCFG()
	{
		return cfg.print();
	}
}