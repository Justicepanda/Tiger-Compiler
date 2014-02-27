package scanner;

import java.util.List;

public abstract class Dfa {
  private final List<State> states;
  private int currState;

  Dfa(List<State> states) {
    this.states = states;
    reset();
  }

  String getStateName() {
    return states.get(currState).getName();
  }

  public void reset() {
    currState = 0;
    resetValue();
  }

  /**
   * Changes the current state of the automaton. It is the responsibility
   * of the user to query tokens, token types, and accept states. If the
   * current state becomes -1 it represents a lexical error.
   */
  public void changeState(char input) {

    adjustState("" + input);
    adjustValue("" + input);
  }

  void adjustState(String input) {
    currState = getCurrState().getDestination(input);
  }

  protected abstract void adjustValue(String input);

  State getCurrState() {
    return states.get(currState);
  }

  int getState() {
    return currState;
  }

  protected abstract void resetValue();
}
