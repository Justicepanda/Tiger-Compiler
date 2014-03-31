package nonterminals;

import parser.ParserRule;

import java.util.ArrayList;
import java.util.List;

class Type extends ParserRule {
  private TypeId typeId;
  private symboltable.Type type;

  @Override
  public void parse() {
    if (peekTypeMatches("ARRAY"))
      matchArrayType();
    else
      matchOtherType();
  }

  private void matchOtherType() {
    typeId = new TypeId();
    matchNonTerminal(typeId);
    type = typeId.getType();
  }

  private void matchArrayType() {
    typeId = new TypeId();
    matchTerminal("ARRAY");
    matchTerminal("LBRACK");
    String dimension = peekTokenValue();
    matchTerminal("INTLIT");
    matchTerminal("RBRACK");
    matchTerminal("OF");
    matchNonTerminal(typeId);
    getTypeOfArray(dimension);
  }

  private void getTypeOfArray(String dimension) {
    obtainType(dimension);
    addDimensionsToType(dimension);
  }

  private void obtainType(String dimension) {
    type = new symboltable.Type(
            typeId.getType().getName(),
            typeId.getType().getActualType() + "[" + dimension + "]");
  }

  private void addDimensionsToType(String dimension) {
    List<Integer> dimensionsSoFar = getDimensionsSoFar();
    dimensionsSoFar.add(Integer.valueOf(dimension));
    type.setAsArray(dimensionsSoFar);
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

  @Override
  public String generateCode() {
    return null;
  }
}