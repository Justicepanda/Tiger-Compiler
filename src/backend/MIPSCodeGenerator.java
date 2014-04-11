package backend;

import java.util.ArrayList;
import java.util.List;

public class MIPSCodeGenerator 
{
	private String inputIrCode;
	private String outputIrCode;
	private DataModel model;
	
	public MIPSCodeGenerator(String irCode, DataModel model)
	{
		this.inputIrCode = irCode;
		this.model = model;
		this.outputIrCode = generateMIPSCode(irCode);
	}

	private String generateMIPSCode(String irCode)
	{
		String[] lines = irCode.split("\n");
		ArrayList<String> newLines = new ArrayList<String>();
		newLines.add(model.getDataHeader());
		newLines.add(".text\n");
		newLines.add(".globl main\n");
		for(int i = 0; i < lines.length; i++)
		{
			if(lines[i].contains("main:"))
			{
				String[] tempLines = new String[lines.length - i];
				for(int j = i; j < lines.length; j++)
				{
					tempLines[j - i] = lines[j];
				}
				lines = tempLines;
			}
			else
			{
				//Something went wrong... the main label doesn't exist for some reason
			}
		}
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
		newLines.add("jr $ra");
		
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
			String r0 = getActualRegister(instrParams[1]);
			String r1 = getActualRegister(instrParams[2]);
			newLines.add("add " + r0 + ", " + r1 + ", 0\n");
			
		}
		else if(isBinaryOp(instrParams[0]))
		{
			String r0 = getActualRegister(instrParams[1]);
			String r1 = getActualRegister(instrParams[2]);
			String r2 = getActualRegister(instrParams[3]);
			
			if(instrParams[0].equals("add"))
			{
				newLines.add("add " + r0 + ", " + r1 + ", " + r2 + "\n");
			}
			else if(instrParams[0].equals("sub"))
			{
				newLines.add("sub " + r0 + ", " + r1 + ", " + r2 + "\n");
			}
			else if(instrParams[0].equals("mult"))
			{
				newLines.add("mul " + r0 + ", " + r1 + ", " + r2 + "\n");
			}
			else if(instrParams[0].equals("div"))
			{
				newLines.add("div " + r0 + ", " + r1 + ", " + r2 + "\n");
			}
			else if(instrParams[0].equals("and"))
			{
				newLines.add("and " + r0 + ", " + r1 + ", " + r2 + "\n");
			}
			else if(instrParams[0].equals("or"))
			{
				newLines.add("or " + r0 + ", " + r1 + ", " + r2 + "\n");
			}
		}
		else if(isBranch(instrParams[0]))
		{
			String r0 = getActualRegister(instrParams[1]);
			String r1 = getActualRegister(instrParams[2]);
			
			if(instrParams[0].equals("breq"))
			{
				newLines.add("beq " + r0 + ", " + r1 + ", " + instrParams[3] + "\n");
			}
			else if(instrParams[0].equals("brneq"))
			{
				newLines.add("bneq " + r0 + ", " + r1 + ", " + instrParams[3] + "\n");
			}
			else if(instrParams[0].equals("brlt"))
			{
				newLines.add("bge " + r0 + ", " + r1 + ", " + instrParams[3] + "\n");
			}
			else if(instrParams[0].equals("brgt"))
			{
				newLines.add("ble " + r0 + ", " + r1 + ", " + instrParams[3] + "\n");
			}
			else if(instrParams[0].equals("brgeq"))
			{
				newLines.add("blt " + r0 + ", " + r1 + ", " + instrParams[3] + "\n");
			}
			else if(instrParams[0].equals("brleq"))
			{
				newLines.add("bgt " + r0 + ", " + r1 + ", " + instrParams[3] + "\n");
			}
		}
		else if(isArrayStore(instrParams[0]))
		{

		}
		else if(isArrayLoad(instrParams[0]))
		{
			
		}
		else if(isReturn(instrParams[0]))
		{
			//EXTRA CREDIT
		}
		else if(isFunctionCall(instrParams[0]))
		{
			//EXTRA CREDIT
		}
		else if(isReturnFunctionCall(instrParams[0]))
		{
			//EXTRA CREDIT
		}
		else if(isLoad(instrParams[0]))
		{
			String r0 = getActualRegister(instrParams[1]);
			
			newLines.add("la $t9" + "," + instrParams[2] + "\n");
			newLines.add("lw " + r0 + ", 0($t9)\n");
		}
		else if(isStore(instrParams[0]))
		{
			String r0 = getActualRegister(instrParams[1]);
			newLines.add("la $t9," + instrParams[2] + "\n");
			newLines.add("sw " + r0 + ", 0($t9)\n");
		}
		else if(isGoTo(instrParams[0]))
			newLines.add("b, " + instrParams[1] + "\n");
		else
			newLines.add(string  + "\n");
		
		return newLines;
	}
	
	private boolean isGoTo(String string) 
	{
		if(string.replace(" ", "").equals("goto"))
			return true;
		return false;
	}

	private String getActualRegister(String string) 
	{
		if(string.equals("r0"))
			return "$t0";
		else if(string.replace(" ", "").equals("r1"))
			return "$t1";
		else if(string.replace(" ", "").equals("r2"))
			return "$t2";
		else if(string.replace(" ", "").equals("r3"))
			return "$t3";
		else if(string.replace(" ", "").equals("r4"))
			return "$t4";
		else if(string.replace(" ", "").equals("r5"))
			return "$t5";
		else if(string.replace(" ", "").equals("r6"))
			return "$t6";
		else if(string.replace(" ", "").equals("r7"))
			return "$t7";
		else if(string.replace(" ", "").equals("r8"))
			return "$t8";
		else if(string.replace(" ", "").equals("r9"))
			return "$t9";
		else
			return "$t0";
	}

	private boolean isStore(String string) 
	{
		if(string.replace(" ", "").equals("store"))
			return true;
		return false;
	}

	private boolean isLoad(String string) 
	{
		if(string.replace(" ", "").equals("load"))
			return true;
		return false;
	}

	public String getGeneratedMIPSCode()
	{
		return outputIrCode;
	}
	
	private boolean isReturnFunctionCall(String string) {
		if(string.replace(" ", "").equals("callr"))
			return true;
		else
			return false;
	}

	private boolean isFunctionCall(String string) {
		if(string.replace(" ", "").equals("call"))
			return true;
		else
			return false;
	}

	private boolean isReturn(String string) {
		if(string.replace(" ", "").equals("return"))
			return true;
		else
			return false;
	}

	private boolean isArrayLoad(String string) 
	{
		if(string.replace(" ", "").equals("array_load"))
			return true;
		else
			return false;
	}

	private boolean isArrayStore(String string) {
		if(string.replace(" ", "").equals("array_store"))
			return true;
		else
			return false;
	}

	private boolean isBranch(String string) 
	{
		if(string.replace(" ", "").equals("breq") || string.replace(" ", "").equals("brneq") || string.replace(" ", "").equals("brlt") 
				|| string.replace(" ", "").equals("brgt") || string.replace(" ", "").equals("brgeq") || string.replace(" ", "").equals("brleq"))
			return true;
		else
			return false;
	}

	private boolean isAssignment(String string) 
	{
		if(string.replace(" ", "").equals("assign"))
			return true;
		return false;
	}

	private boolean isBinaryOp(String string) 
	{
		if(string.replace(" ", "").equals("add") || string.replace(" ", "").equals("sub") || string.replace(" ", "").equals("mult") 
				|| string.replace(" ", "").equals("div") || string.replace(" ", "").equals("and") || string.replace(" ", "").equals("or"))
			return true;
		else
			return false;
	}
}
