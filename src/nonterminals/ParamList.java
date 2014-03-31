package nonterminals;

import parser.ParserRule;
import symboltable.Argument;
import symboltable.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParamList extends ParserRule {
  private Param param;
  private ParamListTail paramListTail;

  @Override
  public void parse() {
    if (peekTypeMatches("ID"))
      matchParam();
  }

  private void matchParam() {
    param = new Param();
    paramListTail = new ParamListTail();
    matchNonTerminal(param);
    matchNonTerminal(paramListTail);
  }

  @Override
  public String getLabel() {
    return "<param-list>";
  }

  public List<Argument> getArguments() {
    if (param == null)
      return null;
    List<Argument> listSoFar = paramListTail.getArguments();
    if (listSoFar == null)
      listSoFar = new ArrayList<Argument>();
    listSoFar.add(param.getArgument());
    Collections.reverse(listSoFar);
    return listSoFar;
  }

  @Override
  public Type getType() {
    return Type.NIL_TYPE;
  }

  @Override
  public String generateCode() {
    return null;
  }
}
