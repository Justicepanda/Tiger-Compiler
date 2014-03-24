package nonterminals;

import parser.ParserRule;
import symboltable.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LValue extends ParserRule {
  private Expression expression;
  private LValue lValue;

  @Override
  public void parse() {
    if (peekTypeMatches("LBRACK")) {
      expression = new Expression();
      lValue = new LValue();
      matchTerminal("LBRACK");
      matchNonTerminal(expression);
      matchTerminal("RBRACK");
      matchNonTerminal(lValue);
    }
  }

  @Override
  public String getLabel() {
    return "<lvalue>";
  }

  @Override
  public Type getType() {
    return null;
  }

  List<Integer> getDimensions() {
    List<Integer> listSoFar = lValue.getDimensions();
    if (listSoFar == null)
      listSoFar = new ArrayList<Integer>();
    listSoFar.add(expression.getValue());
    Collections.reverse(listSoFar);
    return listSoFar;
  }
}
