package backend;

import java.util.ArrayList;
import java.util.HashMap;

import symboltable.Array;
import symboltable.Function;
import symboltable.SymbolTable;
import symboltable.Temporary;
import symboltable.Type;
import symboltable.Variable;

public class DataModel 
{		
	private ArrayList<Variable> vars;
	private ArrayList<Function> funcs;
	private ArrayList<Array> arrays;
	private ArrayList<Temporary> temps;
	
	public DataModel(ArrayList<Variable> vars, ArrayList<Function> funcs, ArrayList<Array> arrays, ArrayList<Temporary> temps)
	{
		loadHashMaps(vars, funcs, arrays, temps);
	}
	
	private void loadHashMaps(ArrayList<Variable> vars, ArrayList<Function> funcs, ArrayList<Array> arrays, ArrayList<Temporary> temps)
	{
		this.vars = vars;
		this.funcs = funcs;
		this.arrays = arrays;
		this.temps = temps;
	}
	
	public String getDataHeader()
	{
		String dataHeader = "	.data\n";
		for(Variable var: vars)
		{
			if(var.getType().isOfSameType(Type.INT_TYPE))
				dataHeader += var.getName() + ": .word 0\n";
			else if(var.getType().isOfSameType(Type.STRING_TYPE))
				dataHeader += var.getName() + " .ascii \"\"\n";
		}
		for(Temporary temp: temps)
		{
			dataHeader += temp.getName() + ": .word 0\n";
		}
		for(Array arr: arrays)
		{
			int size = 0;
			for(int i = 0; i < arr.getDimensions().size(); i++)
			{
				size *= arr.getDimensions().get(i);
			}
			dataHeader += arr.getName() + ": .space " + size + "\n";
		}
		return dataHeader;
	}
}
