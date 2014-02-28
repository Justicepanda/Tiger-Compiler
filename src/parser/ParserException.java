package parser;

import compiler.TokenTuple;

public class ParserException extends RuntimeException {
  ParserException(String s) {
    super(s);
  }
}

class TerminalException extends ParserException {
  public TerminalException(TokenTuple actual, TokenTuple expected) {
    super("\"" + actual.getToken() + "\" is not a valid token. Expected \"" + expected.getToken() + "\".");
  }
}

class NonTerminalException extends ParserException {
  public NonTerminalException(String expected) {
    super(expected);
  }
}