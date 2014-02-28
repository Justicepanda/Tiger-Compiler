package parser;

import compiler.TokenTuple;

import java.util.List;
import java.util.Stack;

public class Parser {
  private Stack<TokenTuple> parsingStack;
  private final ParsingTable parsingTable;
  private final List<Rule> ruleTable;

  public Parser(String tableFileName, String rulesFileName) {
    parsingTable = new ParsingTable(tableFileName);
    ruleTable = new GrammarRulesReader().determineFrom(rulesFileName);
    initParsingStack();
  }

  private void initParsingStack() {
    parsingStack = new Stack<TokenTuple>();
    parsingStack.push(new TokenTuple("EXIT", "$"));
    parsingStack.push(new TokenTuple("NONTERM", "<tiger-program>"));
  }

  public void parse(TokenTuple token) {
    while (topOfStackIsNonTerminal())
      handleNonTerminal(token);
    handleTerminal(token);
  }

  private void handleNonTerminal(TokenTuple token) {
    int rule = findTopOfStackInTable(token);
    if (ruleIsLegal(rule))
      replaceNonTerminalWithContent(rule);
    else
      throw new NonTerminalException(printExpectedTokens(parsingStack.peek()));
  }

  private void replaceNonTerminalWithContent(int rule) {
    parsingStack.pop();
    push(ruleTable.get(rule-1));
    if (parsingStack.peek().getToken().equals("NULL"))
      parsingStack.pop();
  }

  private String printExpectedTokens(TokenTuple expected) {
    String returnValue = "expected ";
    for (int i = 0; i < parsingTable.getWidth() - 1; i++)
      if (ruleIsLegal(parsingTable.getCell(i, expected)))
        returnValue += "'" + parsingTable.getTerminal(i) + "' or ";
    return returnValue.substring(0, returnValue.length() - 4);
  }

  private void handleTerminal(TokenTuple token) {
    if (tokenMatchesStack(token))
      parsingStack.pop();
    else
      throw new TerminalException(token, parsingStack.peek());
  }

  private boolean tokenMatchesStack(TokenTuple token) {
    return token.getType().equals(parsingStack.peek().getType());
  }

  private int findTopOfStackInTable(TokenTuple t) {
    return parsingTable.getCell(t, parsingStack.peek());
  }

  private boolean topOfStackIsNonTerminal() {
    return parsingStack.peek().getType().equals("NONTERM");
  }

  private boolean ruleIsLegal(int rule) {
    return rule != 0;
  }

  private void push(Rule rule) {
    for (int i = rule.getLength() - 1; i >= 0; i--) {
      parsingStack.push(rule.tokens[i]);
    }
  }
}
