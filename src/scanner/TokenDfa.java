package scanner;

import dfa.Dfa;
import dfa.State;

import java.util.List;

public class TokenDfa extends Dfa {
  private String currValue;

  public TokenDfa(List<State> states) {
    super(states);
  }

  protected void adjustValue(String input) {
    currValue += input.charAt(0);
  }

  /**
   * Returns the current token with any trailing characters removed.
   */
  public String getStateValue() {
    return currValue.substring(0, currValue.length() - 1);
  }

  public TokenTuple getToken() {
    return new TokenTuple(getStateName(), getStateValue());
  }

  public boolean isInSpaceState() {
    return getStateName().equals("SPACE");
  }

  public boolean isInErrorState() {
    return getState() == -1;
  }

  public void resetValue() {
    currValue = "";
  }

  public boolean isInAcceptState() {
    return getState() >= 0 &&
            getCurrState().isAcceptState() &&
            !isInSpaceState() &&
            !isInErrorState();
  }
}
