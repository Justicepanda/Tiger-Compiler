package symboltable;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SymbolTable {
  private Map<String, Stack<Entry>> entries;
  private int scope;

  public SymbolTable() {
    entries = new HashMap<String, Stack<Entry>>();
    scope = 1;
  }

  public void moveUpScope() {
    scope++;
  }

  public void moveDownScope() {
    scope--;
    for (Stack<Entry> entry: entries.values())
      if (scope < entry.peek().getScope())
        entry.pop();
  }

  public Entry getEntry(String id) {
    return entries.get(id).peek();
  }

  public void addEntry(String id, Entry entry) {
    entry.setScope(scope);
    if (!entries.containsKey(id))
      entries.put(id, new Stack<Entry>());
    entries.get(id).push(entry);
  }
}
