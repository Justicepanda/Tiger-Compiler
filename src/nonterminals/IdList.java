package nonterminals;

import parser.ParserRule;
import symboltable.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IdList extends ParserRule {
  private final IdListTail idListTail = new IdListTail();
  private String id;

  @Override
  public void parse() {
    id = matchIdAndGetValue();
    matchNonTerminal(idListTail);
  }

  @Override
  public String getLabel() {
    return "<id-list>";
  }

  public List<String> getIds() {
    List<String> listSoFar = idListTail.getIds();
    if (listSoFar == null)
      listSoFar = new ArrayList<String>();
    listSoFar.add(id);
    Collections.reverse(listSoFar);
    return listSoFar;
  }

  @Override
  public Type getType() {
    return null;
  }

  @Override
  public String generateCode() {
    return null;
  }
}
