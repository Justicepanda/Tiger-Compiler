package nonterminals;

import parser.ParserRule;
import symboltable.Array;
import symboltable.Type;
import symboltable.Variable;

public class VariableDeclaration extends ParserRule {
  private final IdList idList = new IdList();
  private final TypeId typeId = new TypeId();
  private final OptionalInit optionalInit = new OptionalInit();
  private Type type;

  @Override
  public void parse() {
    storeLineNumber();
    matchTerminal("VAR");
    matchNonTerminal(idList);
    matchTerminal("COLON");
    matchNonTerminal(typeId);
    matchNonTerminal(optionalInit);
    matchTerminal("SEMI");
    addToTable();
  }

  private void addToTable() {
    type = getType(typeId.getType().getName());
    type.setConstant(false);
    for (String id : idList.getIds())
      checkTypeAndAddToTable(id);
  }

  private void checkTypeAndAddToTable(String id) {
    String value = "nil";
    if (!optionalInit.getType().isExactlyOfType(Type.NIL_TYPE))
      value = checkType();
    addToTable(id, value);
  }

  private String checkType() {
    if (optionalInit.getType().isOfSameType(type))
      return optionalInit.getValue();
    else
      generateException();
    return "nil";
  }

  private void addToTable(String id, String value) {
    if (type.isArray())
      addArrayToTable(id, value);
    else
      addVariableToTable(id, value);
  }

  private void addArrayToTable(String id, String value) {
    Array array = new Array(type, id, type.getDimensions());
    addArray(array);
    array.setValue(value);
  }

  private void addVariableToTable(String id, String value) {
    Variable var = new Variable(type, id);
    var.setValue(value);
    addVariable(var);
  }

  @Override
  public String getLabel() {
    return "<var-declaration>";
  }

  @Override
  public Type getType() {
    return null;
  }

  @Override
  public String generateCode() {
    if (optionalInit.getType() != Type.NIL_TYPE)
    {
    	if(typeId.getType().isArray())
    	{
    		for(String id: idList.getIds())
    		for(int i = 0; i < typeId.getType().getLinearSize(); i++)
    			emit("array_store, " + id + ", " + i + ", " + optionalInit.getValue());
    	}
    	else
    	{
	    	for (String id : idList.getIds())
				emit("assign, " + id + ", " + optionalInit.getValue() + ", ");
    	}
    }
    return null;
  }
}
