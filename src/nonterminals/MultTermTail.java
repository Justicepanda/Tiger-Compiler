package nonterminals;

import parser.ParserRule;
import symboltable.Type;

import java.util.List;

public class MultTermTail extends ParserRule {
  private final LValue lValue = new LValue();
  private final MultTerm2 multTerm2 = new MultTerm2();
  private String id;

  @Override
  public void parse() {
    matchNonTerminal(lValue);
    matchNonTerminal(multTerm2);
  }

  @Override
  public String getLabel() {
    return "<mult-term-tail>";
  }

  @Override
  public Type getType() {
    return multTerm2.getType();
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String generateCode() {
    if (multTerm2.isExpanded()) {
      String op = multTerm2.getOp();
      String a = lValue.generateCode();
      String b = multTerm2.generateCode();
      String temp = newTemp();
      emit(op + ", " + temp + ", " + a + ", " + b);
      return temp;
    }
    else {
      List<Integer> dimensions = getVariable(id).getType().getDimensions();
      if (dimensions != null)
        lValue.setDimensions(dimensions);
      lValue.generateCode();
      return id;
    }
  }

  public boolean isArray() {
    return lValue.isExpanded();
  }
}
