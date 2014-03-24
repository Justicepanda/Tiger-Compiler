package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;
import symboltable.Variable;

public class VariableDeclaration extends ParserRule {
  private final IdList idList = new IdList();
  private final TypeId typeId = new TypeId();
  private final OptionalInit optionalInit = new OptionalInit();

  @Override
  public void parse() {
    matchTerminal("VAR");
    matchNonTerminal(idList);
    matchTerminal("COLON");
    matchNonTerminal(typeId);
    matchNonTerminal(optionalInit);
    matchTerminal("SEMI");

    if (optionalInit.getType() != null) {
      if (optionalInit.getType().isOfSameType(typeId.getType())) {
        for (String id : idList.getIds()) {
          Variable var = new Variable(typeId.getType(), id);
          super.addVariable(var);
          var.setValue(optionalInit.getValue());
        }
      } else {
        throw new SemanticTypeException(typeId.getLineNumber());
      }
    } else {
      for (String id : idList.getIds()) {
        addVariable(typeId.getType(), id);
      }
    }
  }

  @Override
  public String getLabel() {
    return "<var-declaration>";
  }

  @Override
  public Type getType() {
    return null;
  }
}
