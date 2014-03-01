package parser;

import compiler.TokenTuple;

import java.util.List;

public class Parser {
  private final ParsingStack stack;
  private final ParsingTable parsingTable;
  private final List<Rule> ruleTable;

  public Parser(String tableFileName, String rulesFileName) {
    parsingTable = new ParsingTable(tableFileName);
    ruleTable = new GrammarRulesReader().determineFrom(rulesFileName);
    stack = new ParsingStack();
  }

  public void parse(TokenTuple token) {
    while (stack.isTopNonTerminal())
      handleNonTerminal(token);
    handleTerminal(token);
  }

  private void handleNonTerminal(TokenTuple token) {
    int ruleIndex = parsingTable.findIntersection(token, stack.peek());
    if (ruleIsLegal(ruleIndex))
      stack.replaceNonTerminalRule(ruleTable.get(ruleIndex - 1));
    else
      throw new NonTerminalException(printExpectedTokens(stack.peek()));
  }

  private String printExpectedTokens(TokenTuple expected) {
    return "expected " + parsingTable.getOrSeparatedTerminals(expected);
  }

  private void handleTerminal(TokenTuple token) {
    if (stack.topMatches(token))
      stack.pop();
    else
      throw new TerminalException(token, stack.peek());
  }

  private boolean ruleIsLegal(int rule) {
    return rule != 0;
  }
}
