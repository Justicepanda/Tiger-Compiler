package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class EqualityTerm extends ParserRule {
  private final AddTerm addTerm = new AddTerm();
  private final EqualityTerm2 equalityTerm2 = new EqualityTerm2();

  @Override
  public void parse() {
    storeLineNumber();
    matchNonTerminal(addTerm);
    matchNonTerminal(equalityTerm2);
  }

  @Override
  public String getLabel() {
    return "<ineq-term>";
  }

  @Override
  public Type getType() {
    Type t = decideType(addTerm, equalityTerm2);
    if (equalityTerm2.wasExpanded())
      return Type.INT_TYPE;
    else
      return t;
  }

  @Override
  protected String generateCode() {
    return addTerm.generateCode();

  }
}
