package parser;

import compiler.TokenTuple;

import java.util.List;

public class Parser {
  private ParsingStack parsingStack;
  private ParsingTree parsingTree;
  private final ParsingTable parsingTable;
  private final List<Rule> ruleTable;

  public Parser(String tableFileName, String rulesFileName) {
    ruleTable = new GrammarRulesReader().determineFrom(rulesFileName);
    parsingTable = new ParsingTable(tableFileName);
  }

  public void reset() {
    parsingStack = new ParsingStack();
    parsingTree = new ParsingTree();
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
    if (parsingStack.topMatches(token)) {
      parsingTree.addTerminal(token);
      parsingStack.pop();
    } else {
      throw new TerminalException(token, parsingStack.peek());
    }
  }

  private boolean ruleIsLegal(int rule) {
    return rule != 0;
  }

  public String printTree() {
    return parsingTree.print();
  }

  public String printSymbolTable() {
    return parsingTree.getSymbolTable().print();
  }
}
