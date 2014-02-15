package scanner;

public class LexicalException extends RuntimeException {
  public LexicalException(int lineNo, int charNo) {
    super("Lexical Error! Line: " + lineNo + ", character: " + charNo);
  }
}
