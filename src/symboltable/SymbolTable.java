package symboltable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static symboltable.Type.INT_TYPE;
import static symboltable.Type.STRING_TYPE;

public class SymbolTable 
{

	private enum SymbolType
	{
		TYPE, VARIABLE, ARRAY, FUNCTION, TEMPORARY
	}

	private static Map<SymbolType, Map<String, List<Entry>>> table;
	private final int scope;

	public SymbolTable()  {
		scope = 1;
		table = new HashMap<SymbolType, Map<String, List<Entry>>>();
		initTableSections();
		addDefaults();
	}

	private void initTableSections()  {
		table.put(SymbolType.TYPE, new HashMap<String, List<Entry>>());
		table.put(SymbolType.VARIABLE, new HashMap<String, List<Entry>>());
		table.put(SymbolType.ARRAY, new HashMap<String, List<Entry>>());
		table.put(SymbolType.FUNCTION, new HashMap<String, List<Entry>>());
		table.put(SymbolType.TEMPORARY, new HashMap<String, List<Entry>>());
	}

	private void addDefaults() {
		addType(INT_TYPE);
		addType(STRING_TYPE);

    Argument s = new Argument(STRING_TYPE, "s");
    Argument i = new Argument(INT_TYPE, "i");

    List<Argument> s_arg = new ArrayList<Argument>();
    s_arg.add(s);
    List<Argument> i_arg = new ArrayList<Argument>();
    i_arg.add(i);
    List<Argument> sii_args = new ArrayList<Argument>();
    sii_args.add(s);
    sii_args.add(i);
    sii_args.add(i);
    List<Argument> ss_args = new ArrayList<Argument>();
    ss_args.add(s);
    ss_args.add(s);

    addFunction(new Function("print", s_arg, null));
    addFunction(new Function("printi", i_arg, null));
    addFunction(new Function("flush", null, null));
    addFunction(new Function("getchar", null, STRING_TYPE));
    addFunction(new Function("ord", s_arg, INT_TYPE));
    addFunction(new Function("chr", i_arg, STRING_TYPE));
    addFunction(new Function("size", s_arg, INT_TYPE));
    addFunction(new Function("substring", sii_args, STRING_TYPE));
    addFunction(new Function("concat", ss_args, STRING_TYPE));
    addFunction(new Function("not", i_arg, INT_TYPE));
    addFunction(new Function("exit", i_arg, null));
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
	
	public void addTemporary(Temporary entry)
	{
		addEntry(SymbolType.TEMPORARY, entry);
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

  public static Type getType(String id)
  {
    if(table.get(SymbolType.TYPE).get(id) != null)
      return (Type) table.get(SymbolType.TYPE).get(id).get(0);
    return null;

  }

	public static Function getFunction(String functionId) 
	{
		if(table.get(SymbolType.FUNCTION).get(functionId) != null)
			return ((Function)table.get(SymbolType.FUNCTION).get(functionId).get(0));
		return null;
	}
	
	public static Variable getVariable(String variableId) 
	{
		if(table.get(SymbolType.VARIABLE).get(variableId) != null)
			return ((Variable)table.get(SymbolType.VARIABLE).get(variableId).get(0));
		return null;
	}
	
	public static Array getArray(String arrayId) 
	{
		if(table.get(SymbolType.ARRAY).get(arrayId) != null)
			return ((Array)table.get(SymbolType.ARRAY).get(arrayId).get(0));
		return null;
	}
	
	public static Temporary getTemporary(String tempId) 
	{
		if(table.get(SymbolType.TEMPORARY).get(tempId) != null)
			return ((Temporary)table.get(SymbolType.TEMPORARY).get(tempId).get(0));
		return null;
	}
	
	public ArrayList<Variable> getVariables()
	{
		ArrayList<Variable> vars = new ArrayList<Variable>();
		Collection<List<Entry>> entries = table.get(SymbolType.VARIABLE).values();
		Iterator iter = entries.iterator();
		while(iter.hasNext())
		{
			Iterator subIter = ((List<Entry>)iter.next()).iterator();
			while(subIter.hasNext())
			{
				vars.add((Variable)subIter.next());
			}
		}
		return vars;
	}
	
	public ArrayList<Temporary> getTemoraries()
	{
		ArrayList<Temporary> temps = new ArrayList<Temporary>();
		Collection<List<Entry>> entries = table.get(SymbolType.TEMPORARY).values();
		Iterator iter = entries.iterator();
		while(iter.hasNext())
		{
			Iterator subIter = ((List<Entry>)iter.next()).iterator();
			while(subIter.hasNext())
			{
				temps.add((Temporary)subIter.next());
			}
		}
		return temps;
	}
	
	public ArrayList<Function> getFunctions()
	{
		ArrayList<Function> funcs = new ArrayList<Function>();
		Collection<List<Entry>> entries = table.get(SymbolType.FUNCTION).values();
		Iterator iter = entries.iterator();
		while(iter.hasNext())
		{
			Iterator subIter = ((List<Entry>)iter.next()).iterator();
			while(subIter.hasNext())
			{
				funcs.add((Function)subIter.next());
			}
		}
		return funcs;
	}
	
	public ArrayList<Array> getArrays()
	{
		ArrayList<Array> arrays = new ArrayList<Array>();
		Collection<List<Entry>> entries = table.get(SymbolType.ARRAY).values();
		Iterator iter = entries.iterator();
		while(iter.hasNext())
		{
			Iterator subIter = ((List<Entry>)iter.next()).iterator();
			while(subIter.hasNext())
			{
				arrays.add((Array)subIter.next());
			}
		}
		return arrays;
	}
}
