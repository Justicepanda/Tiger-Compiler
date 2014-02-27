package scanner;

public class LexicalException extends RuntimeException {
  public LexicalException(int lineNo, char c) {
    super("Lexical error (line: " + (lineNo + 1) + "): \"" + c + "\" does not begin a valid token.");
  }
}
