package scanner;

public interface State {
  public boolean isNotAcceptState();
  public int getDestination(String input);
  public String getName();
}
