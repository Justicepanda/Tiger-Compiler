package parser;

public class NonTerminalException extends RuntimeException {

  public NonTerminalException(String lineInfo, String expected) {
    super("Parsing error " +
          lineInfo +
          " <-- " +
          expected);
  }
}
