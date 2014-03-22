package symboltable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class SymbolTable {

  private enum SymbolType {
    TYPE,
    VARIABLE,
    ARRAY,
    FUNCTION
  }

  private final Map<SymbolType, Map<String, List<Entry>>> table;
  private final int scope;

  public SymbolTable() {
    scope = 1;
    table = new HashMap<SymbolType, Map<String, List<Entry>>>();
    initTableSections();
    addDefaultTypes();
  }

  private void initTableSections() {
    table.put(SymbolType.TYPE, new HashMap<String, List<Entry>>());
    table.put(SymbolType.VARIABLE, new HashMap<String, List<Entry>>());
    table.put(SymbolType.ARRAY, new HashMap<String, List<Entry>>());
    table.put(SymbolType.FUNCTION, new HashMap<String, List<Entry>>());
  }

  private void addDefaultTypes() {
    addType(new Type("int", "int"));
    addType(new Type("string", "string"));
  }

  public Entry getType(String id) {
    return table.get(SymbolType.TYPE).get(id).get(0);
  }

  public void addType(Type entry) {
    addEntry(SymbolType.TYPE, entry);
  }

  public void addVariable(Entry entry) {
    addEntry(SymbolType.VARIABLE, entry);
  }

  public void addArray(Entry entry) {
    addEntry(SymbolType.ARRAY, entry);
  }

  public void addFunction(FunctionDeclaration entry) {
    addEntry(SymbolType.FUNCTION, entry);
  }

  private void addEntry(SymbolType label, Entry entry) {
    entry.setScope(scope);
    Map<String, List<Entry>> symbolSection = table.get(label);
    if (!symbolSection.containsKey(entry.getName()))
      symbolSection.put(entry.getName(), new ArrayList<Entry>());
    symbolSection.get(entry.getName()).add(entry);
  }

  public String print() {
    String res = "";
    for (SymbolType type: SymbolType.values())
      for (List<Entry> entryList: table.get(type).values())
        for (Entry entry: entryList)
          res += entry + "\n";
    return res;
  }
}
