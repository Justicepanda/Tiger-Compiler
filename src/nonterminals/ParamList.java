package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Argument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParamList extends ParserRule {
  private ParamListTail paramListTail;
  private Param param;

  public ParamList(Scanner scanner) {
    super(scanner);
  }

  @Override
  public void parse() {
    if (peekTypeMatches("ID")) {
      paramListTail = new ParamListTail(scanner);
      param = new Param(scanner);
      matchNonTerminal(param);
      matchNonTerminal(paramListTail);
    }
  }

  @Override
  public String getLabel() {
    return "<param-list>";
  }

  public List<Argument> getArguments() {
    List<Argument> listSoFar = paramListTail.getArguments();
    if (listSoFar == null)
      listSoFar = new ArrayList<Argument>();
    listSoFar.add(param.getArgument());
    Collections.reverse(listSoFar);
    return listSoFar;
  }
}
