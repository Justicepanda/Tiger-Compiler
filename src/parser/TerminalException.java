package parser;

import compiler.TokenTuple;

public class TerminalException extends RuntimeException {
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
