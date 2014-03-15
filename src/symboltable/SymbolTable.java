package symboltable;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class SymbolTable {
  private Map<String, ArrayList<Entry>> entries;
  private int scope;

  public SymbolTable() {
    entries = new HashMap<String, ArrayList<Entry>>();
    scope = 1;
  }

  public void moveUpScope() {
    scope++;
  }

  public void moveDownScope() {
    scope--;
  }

  public Entry getEntry(String id) {
    return entries.get(id).get(scope);
  }

  public void addEntry(String id, Entry entry) {
    entry.setScope(scope);
    if (!entries.containsKey(id))
      entries.put(id, new ArrayList<Entry>());
    entries.get(id).add(entry);
  }
}
