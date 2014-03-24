package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IdList extends ParserRule {
  private IdListTail idListTail;
  private String id;

  public IdList(Scanner scanner) {
    super(scanner);
    idListTail = new IdListTail(scanner);
  }

  @Override
  public void parse() {
    id = peekTokenValue();
    matchTerminal("ID");
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
}
