package backend;

import java.util.ArrayList;
import java.util.List;

import symboltable.SymbolTable;

public class RegisterAllocator 
{
	private SymbolTable table;
	private String inputIrCode;
	private String outputIrCode;
	private DataModel memory;
	
	public RegisterAllocator(SymbolTable table, String irCode)
	{
		this.table = table;
		this.inputIrCode = irCode;
		this.memory = new DataModel(table.getVariables(), table.getFunctions(), table.getArrays(), table.getTemoraries());
		//This is where you can choose which type of register allocation to use
		this.outputIrCode = produceNaiveCode();
	}
	
	public String getGeneratedIrCode()
	{
		return outputIrCode;
	}
	
	public String produceNaiveCode()
	{
		String[] lines = inputIrCode.split("\n");
		ArrayList<String> newLines = new ArrayList<String>();
		for(int i = 0; i < lines.length; i++)
		{
			if(lines[i].contains(":") && !lines[i].substring(lines[i].length() - 1, lines[i].length()).equals(":"))
			{
				List<String> tempLines = generateNewCode(lines[i].split(":")[1]);
				newLines.add(lines[i].split(":")[0]);
				for(int j = 0; j < tempLines.size(); j++)
					newLines.add(tempLines.get(j));
			}
			else
			{
				List<String> tempLines = generateNewCode(lines[i]);
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

	private List<String> generateNewCode(String string) 
	{
		List<String> newLines = new ArrayList<String>();
		//Parse the instruction to find the variables used
		String[] instrParams = string.split(",");
		if(isAssignment(instrParams[0]))
		{
			newLines.add("load, r0," + instrParams[1] + "\n");
			newLines.add("load, r1," + instrParams[2] + "\n");
			newLines.add("assign, r0, r1" + "\n");
			newLines.add("store, r0," + instrParams[1] + "\n");
		}
		else if(isBinaryOp(instrParams[0]))
		{
			newLines.add("load, r0," + instrParams[1] + "\n");
			newLines.add("load, r1," + instrParams[2] + "\n");
			newLines.add("load, r2," + instrParams[3] + "\n");
			newLines.add(instrParams[0] + ", r0, r1, r2" + "\n");
			newLines.add("store, r2," + instrParams[1] + "\n");
		}
		else if(isBranch(instrParams[0]))
		{
			newLines.add("load, r0," + instrParams[1] + "\n");
			newLines.add("load, r1," + instrParams[2] + "\n");
			newLines.add(instrParams[0] + ", r0, r1, " + instrParams[3] + "\n");
		}
		else if(isArrayStore(instrParams[0]))
		{
			newLines.add("load, r0," + instrParams[1]);
			newLines.add("load, r1," + instrParams[2]);
			newLines.add("load, r2," + instrParams[3]);
			newLines.add(instrParams[0] + ", r0, r1, r2" + "\n");
		}
		else if(isArrayLoad(instrParams[0]))
		{
			newLines.add("load, r0," + instrParams[1] + "\n");
			newLines.add("load, r1," + instrParams[2] + "\n");
			newLines.add("load, r2," + instrParams[3] + "\n");
			newLines.add(instrParams[0] + ", r0, r1, r2" + "\n");
		}
		else if(isReturn(instrParams[0]))
		{
			newLines.add("load, r0," + instrParams[1] + "\n");
			newLines.add(instrParams[0] + ", r0" + "\n");
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
}