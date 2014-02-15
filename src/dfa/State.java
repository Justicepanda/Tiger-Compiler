package dfa;

public interface State {
  public boolean isAcceptState();
  public int getDestination(String input);
  public String getName();
}
