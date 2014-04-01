package nonterminals;

import parser.ParserRule;
import symboltable.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LValue extends ParserRule {
  private Expression expression;
  private LValue lValue;
  private List<Integer> dimensions = new ArrayList<Integer>();
  private boolean isExpanded;

  @Override
  public void parse() {
    if (peekTypeMatches("LBRACK"))
      matchLValue();
  }

  private void matchLValue() {
    isExpanded = true;
    expression = new Expression();
    lValue = new LValue();
    matchTerminal("LBRACK");
    matchNonTerminal(expression);
    matchTerminal("RBRACK");
    matchNonTerminal(lValue);
  }

  public void setDimensions(List<Integer> dimensions) {
    if (!dimensions.isEmpty()) {
      dimensions.remove(0);
      this.dimensions = copy(dimensions);
      if (lValue != null)
        lValue.setDimensions(dimensions);
    }
  }

  private List<Integer> copy(List<Integer> in) {
    List<Integer> out = new ArrayList<Integer>();
    for (int i: in)
      out.add(i);
    return out;
  }

  @Override
  public String getLabel() {
    return "<lvalue>";
  }

  @Override
  public Type getType() {
    return Type.NIL_TYPE;
  }

  @Override
  public String generateCode() {
    if (expression != null) {
      String expTemp = expression.generateCode();
      if (!dimensions.isEmpty()) {
        String temp = newTemp();
        emit("mult, " + temp + ", " + expTemp + ", " + computeFollowingDimensionLinearity());
        String lValueCode = lValue.generateCode();
        if (lValueCode != null) {
          String temp1 = newTemp();
          emit("add, " + temp1 + ", " + temp + ", " + lValueCode);
          return temp1;
        }
        return temp;
      }
      return expTemp;
    }
    else
      return null;
  }

  private int computeFollowingDimensionLinearity() {
    int res = 1;
    for (int i: dimensions)
      res *= i;
    return res;
  }

  public boolean isExpanded() {
    return isExpanded;
  }
}
