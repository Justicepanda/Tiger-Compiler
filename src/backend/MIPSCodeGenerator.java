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
			if(lines[i].contains(":") && !lines[i].substring(lines[i].length() - 1, lines[i].length()).equals(":"))
			{
				List<String> tempLines = generateNewCode(lines[i].split(":")[1]);
				newLines.add(lines[i].split(":")[0] + ":");
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
			else if(instrParams[0].equals("addi"))
			{
				r2 = instrParams[3];
				newLines.add("addi " + r0 + ", " + r1 + ", " + r2 + "\n");
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
				newLines.add("ble " + r0 + ", " + r1 + ", " + instrParams[3] + "\n");
			}
			else if(instrParams[0].equals("brgt"))
			{
				newLines.add("bge " + r0 + ", " + r1 + ", " + instrParams[3] + "\n");
			}
			else if(instrParams[0].equals("brgeq"))
			{
				newLines.add("bgt " + r0 + ", " + r1 + ", " + instrParams[3] + "\n");
			}
			else if(instrParams[0].equals("brleq"))
			{
				newLines.add("blt " + r0 + ", " + r1 + ", " + instrParams[3] + "\n");
			}
		}
		else if(isArrayStore(instrParams[0]))
		{  
			//a := arr[1]
			String r0 = getActualRegister(instrParams[1]); //address of arr
			String r1 = getActualRegister(instrParams[2]); //register with 1 in it
			String r2 = getActualRegister(instrParams[3]); //value of a
		    newLines.add("add " + r1 +", " + r1 +", " + r1 + "\n"); //r1 is now 2
		    newLines.add("add " + r1 +", " + r1 +", " + r1 + "\n"); //r1 is now 4
		    newLines.add("add " + r1 + ", " + r1 + ", " + r0 + "\n"); //Holds the address of arr[1]
		    newLines.add("sw "+ r2 +", 0(" + r1 + ")" + "\n");
		}
		else if(isArrayLoad(instrParams[0]))
		{
			String r0 = getActualRegister(instrParams[1]); //a
			String r1 = getActualRegister(instrParams[2]); //arr
			String r2 = getActualRegister(instrParams[3]); //1
			newLines.add("add " + r2 +", " + r2 +", " + r2 + "\n"); //r2 is now 2
		    newLines.add("add " + r2 +", " + r2 +", " + r2 + "\n"); //r2 is now 4
		    newLines.add("add " + r2 + ", " + r2 + ", " + r1 + "\n"); //Holds the address of arr[1]
		    newLines.add("lw $t9, 0(" + r2 + ")" + "\n"); //load value at arr[1] into t9
		    newLines.add("sw $t9, 0(" + r0 + ")\n"); //store value in t9 to address of a
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
		else if(isLoadValue(instrParams[0]))
		{
			if(isNumeric(instrParams[2]))
			{
				String r0 = getActualRegister(instrParams[1]);
				newLines.add("li " + r0 + ", 0x" + Integer.toHexString(Integer.parseInt(instrParams[2].replaceAll("\\s", ""))) + "\n");
			}
			else
			{
				String r0 = getActualRegister(instrParams[1]);
				newLines.add("la " + r0 + "," + instrParams[2] + "\n");
				newLines.add("lw " + r0 + ", 0(" + r0 + ")\n");
			}
		}
		else if(isLoadAddress(instrParams[0]))
		{
			String r0 = getActualRegister(instrParams[1]);
			newLines.add("la " + r0 + ", " + instrParams[2] + "\n");
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
	
	private boolean isLoadValue(String string) {
		if(string.replace(" ", "").equals("loadval"))
			return true;
		return false;
	}

	private boolean isGoTo(String string) 
	{
		if(string.replace(" ", "").equals("goto"))
			return true;
		return false;
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

	private String getActualRegister(String string) 
	{
		if(string.replaceAll("\\s", "").equals("r0"))
			return "$t0";
		else if(string.replaceAll("\\s", "").equals("r1"))
			return "$t1";
		else if(string.replaceAll("\\s", "").equals("r2"))
			return "$t2";
		else if(string.replaceAll("\\s", "").equals("r3"))
			return "$t3";
		else if(string.replaceAll("\\s", "").equals("r4"))
			return "$t4";
		else if(string.replaceAll("\\s", "").equals("r5"))
			return "$t5";
		else if(string.replaceAll("\\s", "").equals("r6"))
			return "$t6";
		else if(string.replaceAll("\\s", "").equals("r7"))
			return "$t7";
		else if(string.replaceAll("\\s", "").equals("r8"))
			return "$t8";
		else if(string.replaceAll("\\s", "").equals("s0"))
			return "$s0";
		else if(string.replaceAll("\\s", "").equals("s1"))
			return "$s1";
		else if(string.replaceAll("\\s", "").equals("s2"))
			return "$s2";
		else if(string.replaceAll("\\s", "").equals("s3"))
			return "$s3";
		else if(string.replaceAll("\\s", "").equals("s4"))
			return "$s4";
		else if(string.replaceAll("\\s", "").equals("s5"))
			return "$s5";
		else if(string.replaceAll("\\s", "").equals("s6"))
			return "$s6";
		else if(string.replaceAll("\\s", "").equals("s7"))
			return "$s7";
		else
			return string;
	}

	private boolean isStore(String string) 
	{
		if(string.replace(" ", "").equals("store"))
			return true;
		return false;
	}

	private boolean isLoadAddress(String string) 
	{
		if(string.replace(" ", "").equals("loadaddr"))
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
				|| string.replace(" ", "").equals("div") || string.replace(" ", "").equals("and") || string.replace(" ", "").equals("or")
				|| string.replaceAll("\\s", "").equals("addi"))
			return true;
		else
			return false;
	}
}
