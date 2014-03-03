package parser;

import compiler.TokenTuple;

import java.util.List;
import java.util.Stack;

public class Parser {
  private final ParsingStack stack;
  private final ParsingTable parsingTable;
  private final List<Rule> ruleTable;
  private final ParsingTree tree;
  private final Stack<Rule> rules;

  public Parser(String tableFileName, String rulesFileName) {
    parsingTable = new ParsingTable(tableFileName);
    ruleTable = new GrammarRulesReader().determineFrom(rulesFileName);
    stack = new ParsingStack();
    tree = new ParsingTree();
    rules = new Stack<Rule>();
    rules.push(Rule.determineFrom("let <declaration-segment> in <stat-seq> end"));
  }

  public void parse(TokenTuple token) {
    while (stack.isTopNonTerminal())
      handleNonTerminal(token);
    handleTerminal(token);
  }

  private void handleNonTerminal(TokenTuple token) {
    int ruleIndex = parsingTable.findIntersection(token, stack.peek());
    if (ruleIsLegal(ruleIndex))
      applyNonTerminal(ruleTable.get(ruleIndex - 1));
    else
      throw new NonTerminalException(printExpectedTokens(stack.peek()));
  }

  private void applyNonTerminal(Rule rule) {
    tree.addChild(stack.peek());
    tree.moveDown();
    rules.peek().moveToNextToken();
    stack.replaceNonTerminalRule(rule);
    rules.push(rule.copy());
    handleFinishedRules();
  }

  private String printExpectedTokens(TokenTuple expected) {
    return "expected " + parsingTable.getOrSeparatedTerminals(expected);
  }

  private void handleTerminal(TokenTuple token) {
    if (stack.topMatches(token))
      applyTerminal();
    else
      throw new TerminalException(token, stack.peek());
  }

  private void applyTerminal() {
    rules.peek().moveToNextToken();
    tree.addChild(stack.pop());
    handleFinishedRules();
  }

  private void handleFinishedRules() {
    while (rules.peek().isFinished()) {
      rules.pop();
      tree.moveUp();
    }
  }

  private boolean ruleIsLegal(int rule) {
    return rule != 0;
  }

  public String printTree() {
    return tree.print();
  }
}
