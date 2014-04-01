package parser;

import compiler.TokenTuple;
import nonterminals.TigerProgram;
import scanner.Scanner;
import symboltable.*;

public class Parser {
	private final Scanner scanner;
  private final SymbolTable table = new SymbolTable();
  private final ParserTree tree = new ParserTree();
  private String printOut = "";

  public Parser(Scanner scanner) {
    ParserRule.reset();
    ParserRule.setParser(this);
    this.scanner = scanner;
	}

	public void parse() {
    TigerProgram tigerProgram = new TigerProgram();
    tigerProgram.parse();
    printOut += tree.print();
    printOut += table.print();
    tigerProgram.generateCode();
    printOut += ParserRule.getGeneratedCode();
  }

  public void addToPrintOut(String toAdd) {
    printOut += toAdd;
  }

  public String getPrintOut() {
    return printOut;
  }

  public void addVariable(Variable variable) {
    table.addVariable(variable);
  }

  public void addArray(Array array) {
    table.addArray(array);
  }

  public void addFunction(Function function) {
    table.addFunction(function);
  }

  public void addType(Type type) {
    table.addType(type);
  }

  public Variable getVariable(String id) {
    return table.getVariable(id);
  }

  public Variable getArray(String id) {
    return table.getArray(id);
  }

  public Function getFunction(String id) {
    return table.getFunction(id);
  }

  public void addToTree(String expected) {
    tree.add(expected);
  }

  public void moveTreeDown() {
    tree.moveDown();
  }

  public void moveTreeUp() {
    tree.moveUp();
  }

  public int getLineNum() {
    return scanner.getLineNum();
  }

  public TokenTuple peekToken() {
    return scanner.peekToken();
  }

  public TokenTuple popToken() {
    return scanner.popToken();
  }

  public String getGeneratedCode() {
    return ParserRule.getGeneratedCode();
  }
}