package parser;

import scanner.Scanner;
import compiler.TokenTuple;

import java.util.List;
import java.util.Stack;

public class Parser {
  private Stack<TokenTuple> parsingStack;
  private final ParsingTable parsingTable;
  private final List<Rule> ruleTable;

  private final Scanner scanner;
  private boolean isLegal;

  public Parser(Scanner scanner, String tableFileName, String rulesFileName) {
    isLegal = true;
    this.scanner = scanner;
    parsingTable = new ParsingTable(tableFileName);
    ruleTable = new GrammarRulesReader().determineFrom(rulesFileName);
    initParsingStack();
  }

  private void initParsingStack() {
    parsingStack = new Stack<TokenTuple>();
    parsingStack.push(new TokenTuple("EXIT", "$"));
    parsingStack.push(new TokenTuple("NONTERM", "<tiger-program>"));
  }

  public void parse() {
    while (scanner.hasMoreTokens() && isLegal)
      handleNextToken(scanner.getNextToken());
  }

  void handleNextToken(TokenTuple token) {
    while (topOfStackIsNonTerminal() && noErrorsEncountered())
      handleNonTerminal(token);
    handleTerminal(token);
  }

  private void handleNonTerminal(TokenTuple token) {
    int rule = findTopOfStackInTable(token);
    if (ruleIsLegal(rule))
      replaceNonTerminalWithContent(rule);
    else {
      handleNonTerminalError();
    }
  }

  private void replaceNonTerminalWithContent(int rule) {
    parsingStack.pop();
    push(ruleTable.get(rule-1));
    if (parsingStack.peek().getToken().equals("NULL"))
      parsingStack.pop();
  }

  private void handleNonTerminalError() {
    System.err.println("\nParsing error " +
            scanner.getLineInfo() +
            " <-- " +
            printExpectedTokens(parsingStack.peek()));
    isLegal = false;
  }

  String printExpectedTokens(TokenTuple expected) {
    String returnValue = "expected ";
    for (int i = 0; i < parsingTable.getWidth() - 1; i++)
      if (ruleIsLegal(parsingTable.getCell(i, expected)))
        returnValue += "'" + parsingTable.getTerminal(i) + "' or ";
    return returnValue.substring(0, returnValue.length() - 3);
  }

  private void handleTerminal(TokenTuple token) {
    if (tokenMatchesStack(token))
      parsingStack.pop();
    else if (noErrorsEncountered())
      handleTerminalError(token);
  }

  private boolean tokenMatchesStack(TokenTuple token) {
    return token.getType().equals(parsingStack.peek().getType());
  }

  private void handleTerminalError(TokenTuple token) {
    System.err.println("\nParsing error " +
            scanner.getLineInfo() +
            " <-- \"" +
            token.getToken() +
            "\" is not a valid token. Expected \"" +
            parsingStack.peek().getToken() +
            "\".");
    isLegal = false;
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

  public boolean noErrorsEncountered() {
    return isLegal && scanner.isValid();
  }

  private void push(Rule rule) {
    for (int i = rule.getLength() - 1; i >= 0; i--) {
      parsingStack.push(rule.tokens[i]);
    }
  }
}
