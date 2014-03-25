package parser;

import compiler.TokenTuple;
import scanner.Scanner;
import symboltable.*;

import java.util.List;

public abstract class ParserRule 
{
  private static boolean isDebug;
	private static Scanner scanner;
	private static SymbolTable symbolTable = new SymbolTable();
	private static SimpleTree tree = new SimpleTree();

  public void setDebug() {
    this.isDebug = true;
  }

  private int lineNumber;

  public static void setScanner(Scanner scanner) {
    ParserRule.scanner = scanner;
  }

	public static void reset() {
		symbolTable = new SymbolTable();
		tree = new SimpleTree();
	}

	protected void matchTerminal(String expected) {
		tree.add(expected);
		TokenTuple actual = scanner.popToken();
		if (!actual.getType().equals(expected))
			throw new TerminalException(actual, new TokenTuple(expected, expected));
    if (isDebug)
		  System.out.print(actual.getType() + " ");
	}

	protected boolean peekTypeMatches(String toMatch) 
	{
		return scanner.peekToken().getType().equals(toMatch);
	}

	protected String peekTokenValue()
	{
		return scanner.peekToken().getToken();
	}

  protected String matchIdAndGetValue() {
    String value = peekTokenValue();
    matchTerminal("ID");
    return value;
  }

	protected void matchNonTerminal(ParserRule expected) {
		tree.add(expected.getLabel());
		tree.moveDown();
		expected.parse();
		tree.moveUp();
	}

	protected abstract void parse();

	protected abstract String getLabel();

	public abstract Type getType();

	public static String print() {
		return tree.print() + symbolTable.print();
	}

  protected Type getTypeOfVariable(String id) {
    return getVariable(id).getType();
  }

  protected void addFunction(String id, List<Argument> arguments, Type type) {
    symbolTable.addFunction(new Function(id, arguments, type));
  }

  protected Function getFunction(String id) {
    return symbolTable.getFunction(id);
  }

  protected void addVariable(Type type, String id) {
    symbolTable.addVariable(new Variable(type, id));
  }

  protected void addVariable(Variable var) {
    symbolTable.addVariable(var);
  }

  protected Variable getVariable(String id) {
    Variable var = symbolTable.getVariable(id);
    if (var == null)
      var = symbolTable.getArray(id);
    if (var == null)
      throw new NoSuchIdentifierException(id);
    return var;
  }

  protected void addType(String id, Type type) {
    Type toAdd = new Type(id, type.getActualType());
    toAdd.setAsArray(type.getDimensions());
    symbolTable.addType(toAdd);
  }

  public void addArray(Type type, String id, List<Integer> dimensions) {
    symbolTable.addArray(new Array(type, id, dimensions));
  }

  protected Type getType(String id) {
    Type t = SymbolTable.getType(id);
    if (t == null)
      throw new NoSuchTypeException(id);
    return t;
  }

  protected void storeLineNumber() {
    lineNumber = scanner.getLineNum();
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

  public void addArray(Array array) {
    symbolTable.addArray(array);
  }
}
