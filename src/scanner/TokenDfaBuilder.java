package scanner;

import dfa.Dfa;
import dfa.State;
import dfabuilder.DfaBuilder;
import java.util.List;

/**
 * The user is responsible for passing a valid csv sheet.
 *
 * The first row of the sheet should be a header row.
 * The header for each column should be the character for
 * the transition of the state, the word DEFAULT, the word SPACE
 * , the word comma, the string 0-9, or the string
 * ~`!@#$%?. For a given row (state) the value in each
 * of these columns represents which state to move to
 * after receiving the character in the header.
 *
 * i.e. if state 2 should move to 10 after receiving an
 * 'a', then the value in the column 'a' for state 2 should
 * be 10.
 *
 * All remaining rows should represent states for the dfa.
 *
 * The first column should be a counter starting at
 * 0 for the second row (first state). The next column should
 * define the name of the state. Accept states should have
 * a name that matches the name of the type of token to be
 * returned.
 *
 * The next column should be the DEFAULT column that defines
 * where the state should go if no specific value has been
 * defined for that state.
 *
 * The final column should have the value 'yes' if the state in
 * question is an accept state.
 */
public class TokenDfaBuilder extends DfaBuilder {

  public TokenDfaBuilder() {
    super();
  }

  protected TokenState getNextState() {
    String[] line = getNextLine();
    TokenState state = new TokenState(line[1], line[line.length-1].equals("yes"));
    for (int i = 2; i < line.length-1; i++)
      if (!line[i].equals(""))
        state.addDestination(getHeaderEntry(i), Integer.parseInt(line[i]));
    return state;
  }

  @Override
  protected Dfa buildDfa(List<State> states) {
    return new TokenDfa(states);
  }
}
