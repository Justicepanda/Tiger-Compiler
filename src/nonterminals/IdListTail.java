package nonterminals;

import parser.ParserRule;
import symboltable.Type;

import java.util.ArrayList;
import java.util.List;

public class IdListTail extends ParserRule {
  private IdListTail idListTail;
  private String id;

  @Override
  public void parse() {
    if (peekTypeMatches("COMMA"))
      matchList();
  }

  private void matchList() {
    idListTail = new IdListTail();
    matchTerminal("COMMA");
    id = matchIdAndGetValue();
    matchNonTerminal(idListTail);
  }

  @Override
  public String getLabel() {
    return "<id-list-tail>";
  }

  public List<String> getIds() {
    if (id == null)
      return null;
    List<String> listSoFar = idListTail.getIds();
    if (listSoFar == null)
      listSoFar = new ArrayList<String>();
    listSoFar.add(id);
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
