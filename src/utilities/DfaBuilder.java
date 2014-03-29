package utilities;

import scanner.Dfa;
import scanner.State;

import java.util.ArrayList;
import java.util.List;

public abstract class DfaBuilder {
  private final CsvReader reader;
  private String[] header;

  protected DfaBuilder() {
    reader = new CsvReader();
  }

  public Dfa buildFrom(String filename) {
    reader.read(filename);
    header = reader.getHeader();
    List<State> states = getStates();
    return buildDfa(states);
  }

  private List<State> getStates() {
    List<State> states = new ArrayList<State>();
    while (statesRemain())
      states.add(getNextState());
    return states;
  }

  private boolean statesRemain() {
    return reader.hasLine();
  }

  protected abstract State getNextState();

  protected String getHeaderEntry(int i) {
    return header[i];
  }

  protected String[] getNextLine() {
    return reader.getLine();
  }

  protected abstract Dfa buildDfa(List<State> states);
}
