package nonterminals;

import parser.ParserRule;

import java.util.ArrayList;
import java.util.List;

class Type extends ParserRule {
  private TypeId typeId;
  private symboltable.Type type;

  @Override
  public void parse() {
    if (peekTypeMatches("ARRAY")) {
      typeId = new TypeId();
      matchTerminal("ARRAY");
      matchTerminal("LBRACK");
      String dimension = peekTokenValue();
      matchTerminal("INTLIT");
      matchTerminal("RBRACK");
      matchTerminal("OF");
      matchNonTerminal(typeId);
      type = new symboltable.Type(
              typeId.getType().getName(),
              typeId.getType().getActualType() + "[" + dimension + "]");

      List<Integer> dimensionsSoFar = getDimensionsSoFar();
      dimensionsSoFar.add(Integer.valueOf(dimension));
      type.setAsArray(dimensionsSoFar);
    } else {
      typeId = new TypeId();
      matchNonTerminal(typeId);
      type = typeId.getType();
    }
  }

  private List<Integer> getDimensionsSoFar() {
    List<Integer> dimensionsSoFar = typeId.getType().getDimensions();
    if (dimensionsSoFar == null)
      dimensionsSoFar = new ArrayList<Integer>();
    return dimensionsSoFar;
  }

  @Override
  public String getLabel() {
    return "<type>";
  }

  @Override
  public symboltable.Type getType() {
    return type;
  }
}