package parser;

public class NoSuchTypeException extends RuntimeException {
  public NoSuchTypeException(String id) {
    super("No type exists with the name " + id);
  }
}
