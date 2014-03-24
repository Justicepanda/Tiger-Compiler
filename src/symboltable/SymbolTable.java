package symboltable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import parser.ParserRule;

public class SymbolTable 
{

	private enum SymbolType
	{
		TYPE, VARIABLE, ARRAY, FUNCTION
	}

	private static Map<SymbolType, Map<String, List<Entry>>> table;
	private final int scope;

	public SymbolTable() 
	{
		scope = 1;
		table = new HashMap<SymbolType, Map<String, List<Entry>>>();
		initTableSections();
		addDefaultTypes();
	}

	private void initTableSections() 
	{
		table.put(SymbolType.TYPE, new HashMap<String, List<Entry>>());
		table.put(SymbolType.VARIABLE, new HashMap<String, List<Entry>>());
		table.put(SymbolType.ARRAY, new HashMap<String, List<Entry>>());
		table.put(SymbolType.FUNCTION, new HashMap<String, List<Entry>>());
	}

	private void addDefaultTypes() 
	{
		addType(new Type("int", "int"));
		addType(new Type("string", "string"));
	}

	public static Type getType(String id)
{
		return (Type) table.get(SymbolType.TYPE).get(id).get(0);
	}

	public void addType(Type entry) 
	{
		addEntry(SymbolType.TYPE, entry);
	}

	public void addVariable(Entry entry) 
	{
		addEntry(SymbolType.VARIABLE, entry);
	}

	public void addArray(Entry entry) 
	{
		addEntry(SymbolType.ARRAY, entry);
	}

	public void addFunction(Function entry) 
	{
		addEntry(SymbolType.FUNCTION, entry);
	}

	private void addEntry(SymbolType label, Entry entry) 
	{
		entry.setScope(scope);
		Map<String, List<Entry>> symbolSection = table.get(label);
		if (!symbolSection.containsKey(entry.getName()))
			symbolSection.put(entry.getName(), new ArrayList<Entry>());
		symbolSection.get(entry.getName()).add(entry);
	}

	public String print() 
	{
		String res = "";
		for (SymbolType type : SymbolType.values())
			for (List<Entry> entryList : table.get(type).values())
				for (Entry entry : entryList)
					res += entry + "\n";
		return res;
	}

	public Function getFunction(String functionId) 
	{
		if(table.get(SymbolType.FUNCTION).get(functionId) != null)
			return ((Function)table.get(SymbolType.FUNCTION).get(functionId).get(0));
		return null;
	}
	
	public Variable getVariable(String variableId) 
	{
		if(table.get(SymbolType.VARIABLE).get(variableId) != null)
			return ((Variable)table.get(SymbolType.VARIABLE).get(variableId).get(0));
		return null;
	}
	
	public Array getArray(String arrayId) 
	{
		if(table.get(SymbolType.ARRAY).get(arrayId) != null)
			return ((Array)table.get(SymbolType.ARRAY).get(arrayId).get(0));
		return null;
	}
}
