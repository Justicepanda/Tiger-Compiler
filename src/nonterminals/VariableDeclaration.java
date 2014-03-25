package nonterminals;

import parser.ParserRule;
import symboltable.Array;
import symboltable.Type;
import symboltable.Variable;

public class VariableDeclaration extends ParserRule {
  private final IdList idList = new IdList();
  private final TypeId typeId = new TypeId();
  private final OptionalInit optionalInit = new OptionalInit();

  @Override
  public void parse() {
    storeLineNumber();
    matchTerminal("VAR");
    matchNonTerminal(idList);
    matchTerminal("COLON");
    matchNonTerminal(typeId);
    matchNonTerminal(optionalInit);
    matchTerminal("SEMI");

    Type type = getType(typeId.getType().getName());

    for (String id : idList.getIds()) {
      if (!optionalInit.getType().isExactlyOfType(Type.NIL_TYPE)) {
        if (optionalInit.getType().isOfSameType(type)) {
          if (type.isArray()) {
            type.setConstant(false);
            Array array = new Array(type, id, type.getDimensions());
            array.setValue(optionalInit.getValue());
            super.addArray(array);
          } else {
            type.setConstant(false);
            Variable var = new Variable(type, id);
            var.setValue(optionalInit.getValue());
            super.addVariable(var);
          }
        } else
          generateException();
      } else {
        if (type.isArray()) {
          type.setConstant(false);
          Array array = new Array(type, id, type.getDimensions());
          super.addArray(array);
        } else {
          type.setConstant(false);
          Variable var = new Variable(type, id);
          super.addVariable(var);
        }
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
