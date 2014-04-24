package parser;

import compiler.TokenTuple;
import symboltable.*;

import java.util.List;

public abstract class ParserRule {
  private static Parser parser;
  private static String generatedCode;
  private static NameMaker nameMaker;

  public static void setParser(Parser parser) {
    ParserRule.parser = parser;
  }

  public static void reset() {
    generatedCode = "";
    nameMaker = new NameMaker();
  }

  public static String getGeneratedCode() {
    return generatedCode;
  }

  protected static void emit(String line) {
    generatedCode += line + "\n";
  }
  
  protected static void emitLabel(String line)
  {
	  generatedCode += line;
  }

  private int lineNumber;

  protected void matchTerminal(String expected) {
    parser.addToTree(expected);
		TokenTuple actual = parser.popToken();
		if (!actual.getType().equals(expected))
			throw new ParserException(actual, new TokenTuple(expected, expected));
    else
      parser.addToPrintOut(expected + " ");
	}

	protected boolean peekTypeMatches(String toMatch)
	{
		return parser.peekToken().getType().equals(toMatch);
	}

	protected String peekTokenValue()
	{
		return parser.peekToken().getToken();
	}

  protected String matchIdAndGetValue() {
    String value = peekTokenValue();
    matchTerminal("ID");
    return value;
  }

	protected void matchNonTerminal(ParserRule expected) {
    parser.addToTree(expected.getLabel());
    parser.moveTreeDown();
		expected.parse();
    parser.moveTreeUp();
	}

	public abstract void parse();

	protected abstract String getLabel();

	protected abstract Type getType();

  public abstract String generateCode();

  protected Type getTypeOfVariable(String id) {
    return getVariable(id).getType();
  }

  protected void addFunction(String id, List<Argument> arguments, Type type) {
    parser.addFunction(new Function(id, arguments, type));
  }

  protected Function getFunction(String id) {
    return parser.getFunction(id);
  }

  protected void addVariable(Variable var) {
    parser.addVariable(var);
  }

  protected Variable getVariable(String id) {
    Variable var = retrieveVariable(id);
    if (var == null)
      throw new NoSuchIdentifierException(id);
    return var;
  }

  private Variable retrieveVariable(String id) {
    Variable var = parser.getVariable(id);
    if (var == null)
      var = parser.getArray(id);
    return var;
  }

  protected void addType(String id, Type type) {
    Type toAdd = new Type(id, type.getActualType());
    toAdd.setAsArray(type.getDimensions());
    parser.addType(toAdd);
  }

  protected Type getType(String id) {
    Type t = SymbolTable.getType(id);
    if (t == null)
      throw new NoSuchTypeException(id);
    return t;
  }

  protected void storeLineNumber() {
    lineNumber = parser.getLineNum();
  }

  protected void generateException() {
    throw new SemanticTypeException(lineNumber);
  }

  protected Type decideType(ParserRule... rules) {
    if (rules[0] == null)
      return Type.NIL_TYPE;
    if (rulesMatchType(rules))
      return agreedUponType(rules);
    else
      generateException();
    return null;
  }

  protected boolean rulesMatchType(ParserRule... rules) {
    Type type = rules[0].getType();
    for (ParserRule rule: rules)
      if (!rule.getType().isOfSameType(type))
        return false;
    return true;
  }

  private Type agreedUponType(ParserRule... rules) {
    for (ParserRule rule: rules)
      if (!rule.getType().isExactlyOfType(Type.NIL_TYPE))
        return rule.getType();
    return Type.NIL_TYPE;
  }

  protected void addArray(Array array) {
    parser.addArray(array);
  }

  protected String newTemp() {
	  String id = nameMaker.newTemp();
	 parser.getTable().addTemporary(new Temporary(Type.NIL_TYPE, id));
    return id;
  }

  public String newLabel(String labelBase) {
    return nameMaker.newLabel(labelBase);
  }
}
