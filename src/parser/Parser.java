package parser;

import compiler.TokenTuple;
import symboltable.*;

import java.util.List;
import java.util.ArrayList;

public class Parser {
  private ParsingStack parsingStack;
  private ParsingTree parsingTree;
  private SymbolTable symbolTable;
  private final ParsingTable parsingTable;
  private final List<Rule> ruleTable;

  public Parser(String tableFileName, String rulesFileName) {
    ruleTable = new GrammarRulesReader().determineFrom(rulesFileName);
    parsingTable = new ParsingTable(tableFileName);
    symbolTable = new SymbolTable();
  }

  public void reset() {
    parsingStack = new ParsingStack();
    parsingTree = new ParsingTree();
    symbolTable = new SymbolTable();
  }

  public void parse(TokenTuple token) {
    while (parsingStack.isTopNonTerminal())
      handleNonTerminal(token);
    handleTerminal(token);
  }

  private void handleNonTerminal(TokenTuple token) {
    int ruleIndex = parsingTable.findIntersection(token, parsingStack.peek());
    if (ruleIsLegal(ruleIndex))
      applyNonTerminal(ruleTable.get(ruleIndex - 1));
    else
      throw new NonTerminalException(printExpectedTokens(parsingStack.peek()));
  }

  private void applyNonTerminal(Rule rule) {
    parsingTree.addNonTerminal(parsingStack.peek(), rule);
    parsingStack.replaceNonTerminalRule(rule);
  }

  private String printExpectedTokens(TokenTuple expected) {
    return "expected " + parsingTable.getOrSeparatedTerminals(expected);
  }

  private void handleTerminal(TokenTuple token) {
    if (parsingStack.topMatches(token))
    {
      parsingTree.addTerminal(token);
      parsingStack.pop();
    }
    else
    {
      throw new TerminalException(token, parsingStack.peek());
    }
  }
  
  private boolean ruleIsLegal(int rule) {
    return rule != 0;
  }

  public String printTree() {
    return parsingTree.print();
  }
  
  public void printSymbolTable()
  {
	  symbolTable.print();
  }
  
  public void buildSymbolTable()
  {
	  traverseSymbolTable(parsingTree.getRoot());
  }
  
  public void traverseSymbolTable(Node parent)
  {
	  for(int i = 0; i < parent.children.size(); i++)
	  {
		  if(parent.children.size() > 0)
		  {
			  visit(parent.children.get(i));
			  traverseSymbolTable(parent.children.get(i));
		  }
	  }
  }
  
  public void visit(Node node)
  {
    new SymbolTableBuilder(node).invoke();
  }

  private class SymbolTableBuilder {
    private Node node;
    private ArrayList<Variable> variables;
    private ArrayList<Array> arrays;
    private Type variableType;
    private Node currentIdListNode;

    public SymbolTableBuilder(Node node) {
      this.node = node;
    }

    public void invoke() {
      variables = new ArrayList<Variable>();
      arrays = new ArrayList<Array>();
      if(node.token.getToken().equals("<type-declaration>"))
        addTypeToTable();
      else if(node.token.getToken().equals("<var-declaration>"))
      {
        //You can instantiate more than one variable in a single line
        currentIdListNode = node.children.get(1);
        variableType = symbolTable.getType(node.children.get(3).children.get(0).token.getToken());

        //If type is an array, create an array instead of a variable
        if(variableType.getActualType().split(" ")[0].equals("array"))
          addArrayToTable();
        else
          addVariableToTable();
      }
      else if(node.token.getToken().equals("<funct-declaration>"))
      {
        addFunctionToTable();
      }
    }

    private void addFunctionToTable() {
      String functionName = node.children.get(1).token.getToken();
      Type type = null;
      if (node.children.get(5).children.size() > 0) {
        type = symbolTable.getType(node.children.get(5).children.get(1).children.get(0).token.getToken());
      }
      else
        type = new Type(null, null);

      ArrayList<Argument> arguments = new ArrayList<Argument>();
      if (node.children.get(3).children.size() > 0) {

        Node paramList = node.children.get(3);
        Node param = paramList.children.get(0);
        Type argType = symbolTable.getType(param.children.get(2).children.get(0).token.getToken());
        arguments.add(new Argument(argType, param.children.get(0).token.getToken()));

        param = param.children.get(1);
        while (param.children.size() > 0) {
          argType = symbolTable.getType(param.children.get(2).children.get(0).token.getToken());
          arguments.add(new Argument(argType, param.children.get(0).token.getToken()));
        }
      }


      FunctionDeclaration fd = new FunctionDeclaration(functionName, arguments, type);
      symbolTable.addFunction(functionName, fd);
    }

    private void addTypeToTable() {
      String typeName = (node.children.get(1)).token.getToken();
      String actualType = "";

      //The children of <type>
      for(int i = 0; i < node.children.get(3).children.size(); i++)
      {
        //This is the right value of a type assignment
        if(node.children.get(3).children.get(i).token.getToken().equals("<type-id>"))
          actualType += node.children.get(3).children.get(i).getLastChild().token.getToken() + " ";
        else
          actualType += node.children.get(3).children.get(i).token.getToken() + " ";
      }

      symbolTable.addType(new Type(typeName, actualType));
    }

    private void addVariableToTable() {
      String variableValue = "";

      if(node.children.get(4).children.size() > 0)
      {
        if(getLiteralValue().equals("-"))
          variableValue = getLiteralValue() + getNegativeValue();
        else
          variableValue = getLiteralValue();
      }

      Variable newVar = new Variable(variableType, currentIdListNode.children.get(0).token.getToken());
      newVar.setValue(variableValue);
      variables.add(newVar);
      currentIdListNode = currentIdListNode.children.get(1);

      while(currentIdListNode.children.size() > 0)
      {
        newVar = new Variable(variableType, currentIdListNode.children.get(1).token.getToken());
        newVar.setValue(variableValue);
        variables.add(newVar);
        currentIdListNode = currentIdListNode.children.get(2);
      }

      for(Variable var: variables)
      {
        symbolTable.addVariable(var.getName(), var);
      }
    }

    private String getNegativeValue() {
      return node.children.get(4).children.get(1).children.get(1).token.getToken();
    }

    private String getLiteralValue() {
      return node.children.get(4).children.get(1).children.get(0).token.getToken();
    }

    private void addArrayToTable() {
      String arrayValue = "";

      if(node.children.get(4).children.size() > 0)
      {
        if(getLiteralValue().equals("-"))
          arrayValue = getLiteralValue() + getNegativeValue();
        else
          arrayValue = getLiteralValue();
      }
      String[] temp = variableType.getActualType().split("\\[");
      ArrayList<Integer> dimensions = new ArrayList<Integer>();
      for(int i = 1; i < temp.length; i++)
      {
        dimensions.add(new Integer(temp[i].split(" ")[1]));
      }

      Array newArray = new Array(variableType, currentIdListNode.children.get(0).token.getToken(), dimensions);
      newArray.setValue(arrayValue);
      arrays.add(newArray);
      currentIdListNode = currentIdListNode.children.get(1);

      while(currentIdListNode.children.size() > 0)
      {
        newArray = new Array(variableType, currentIdListNode.children.get(1).token.getToken(), dimensions);
        newArray.setValue(arrayValue);
        arrays.add(newArray);
        currentIdListNode = currentIdListNode.children.get(2);
      }

      for(Array arr: arrays)
      {
        symbolTable.addArray(arr.getName(), arr);
      }
    }
  }
}
