package parser;

import compiler.TokenTuple;
import scanner.Scanner;
import symboltable.*;

import java.util.List;

public abstract class ParserRule 
{
	private static Scanner scanner;
	private static SymbolTable symbolTable = new SymbolTable();
	private static SimpleTree tree = new SimpleTree();

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
		System.out.print(actual.getType() + " ");
	}

	protected boolean peekTypeMatches(String toMatch) 
	{
		return scanner.peekToken().getType().equals(toMatch);
	}

  protected boolean rulesMatchType(ParserRule... rules) {
    Type type = rules[0].getType();
    for (ParserRule rule: rules)
      if (!rule.getType().isOfSameType(type))
        return false;
    return true;
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
    return symbolTable.getVariable(id).getType();
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
    return symbolTable.getVariable(id);
  }

  protected void addType(String id, Type type) {
    symbolTable.addType(new Type(id, type.getActualType()));
  }

  protected Type getType(String id) {
    return SymbolTable.getType(id);
  }

  protected void storeLineNumber() {
    lineNumber = scanner.getLineNum();
  }

  protected void generateException() {
    throw new SemanticTypeException(lineNumber);
  }
}
