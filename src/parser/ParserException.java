package parser;

import compiler.TokenTuple;

public class ParserException extends RuntimeException {
  ParserException(String s) {
    super(s);
  }
}

class TerminalException extends ParserException {
  public TerminalException(String lineInfo, TokenTuple actual, TokenTuple expected) {
    super("Parsing error " +
            lineInfo +
            "<-- \"" +
            actual.getToken() +
            "\" is not a valid token. Expected \"" +
            expected.getToken() +
            "\".");
  }
}

class NonTerminalException extends ParserException {
  public NonTerminalException(String lineInfo, String expected) {
    super("Parsing error " +
          lineInfo +
          " <-- " +
          expected);
  }
}