package scanner;

public class LexicalException extends RuntimeException {
  public LexicalException(int lineNo, int charNo, String text) {
    super("Lexical Error! Line: " + lineNo + ", character: " + charNo + ", " + "text: " + text);
  }
}
