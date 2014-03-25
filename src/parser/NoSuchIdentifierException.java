package parser;

public class NoSuchIdentifierException extends RuntimeException {
  public NoSuchIdentifierException(String id) {
    super("No identifier exists with the name " + id);
  }
}
