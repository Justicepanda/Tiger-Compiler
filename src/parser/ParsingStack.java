package parser;

import compiler.TokenTuple;

import java.util.Stack;

class ParsingStack {
  private final Stack<TokenTuple> stack;

  ParsingStack() {
    stack = new Stack<TokenTuple>();
    stack.push(new TokenTuple("EXIT", "$"));
    stack.push(new TokenTuple("NONTERM", "<tiger-program>"));
  }

  boolean isTopNonTerminal() {
    return stack.peek().getType().equals("NONTERM");
  }

  void replaceNonTerminalRule(Rule rule) {
    pop();
    push(rule);
    if (stack.peek().getToken().equals("NULL"))
      pop();
  }

  private void push(Rule rule) {
    for (int i = rule.getLength() - 1; i >= 0; i--)
      stack.push(rule.getToken(i));
  }

  boolean topMatches(TokenTuple token) {
    return token.getType().equals(stack.peek().getType());
  }

  TokenTuple peek() {
    return stack.peek();
  }

  TokenTuple pop() {
    return stack.pop();
  }
}
