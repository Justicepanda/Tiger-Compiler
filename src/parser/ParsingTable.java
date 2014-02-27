package parser;

import utilities.SvReader;
import frontend.TokenTuple;

import java.util.ArrayList;
import java.util.List;

public class ParsingTable {
  private int[][] table;
  private SvReader fileReader;
  private List<String> terms;
  private String[] nonTerminals; //Starts at index 0

  public ParsingTable(String fileName) {

    nonTerminals = new String[47];
    table = new int[47][50];

    fileReader = new SvReader(',');
    fileReader.read(fileName);
    int currentLine = 0;

    while (fileReader.hasLine()) {
      terms = new ArrayList<String>();
      String[] tempTerminals = fileReader.getHeader();

      //Quick hack.. couldn't figure out why it was adding quotation marks into the mix
      for (int i = 0; i < 50; i++)
        terms.add(i, tempTerminals[i+2]);

      String[] cells = fileReader.getLine();
      for (int i = 0; i < 50; i++) {
        try {
          nonTerminals[currentLine] = cells[0];
          table[currentLine][i] = Integer.parseInt(cells[i + 1]);
        } catch (Exception ex) {
          //It was an empty cell, don't do anything
        }
      }
      currentLine++;
    }
  }

  public int getTerminalIndex(TokenTuple terminal) {
    for (int i = 0; i < terms.size(); i++)
      if (terms.get(i).equals(terminal.getToken()) || terms.get(i).equalsIgnoreCase(terminal.toString()))
        return i;
    return -1;
  }

  public int getNonTerminalIndex(TokenTuple nonTerminal) {
    for (int i = 0; i < nonTerminals.length; i++) {
      if (nonTerminals[i].equals(nonTerminal.getToken())) {
        return i;
      }
    }

    return -1;
  }

  public int getCell(TokenTuple terminal, TokenTuple nonTerminal) {
    int term = getTerminalIndex(terminal);
    int nonterm = getNonTerminalIndex(nonTerminal);

    if (term != -1 && nonterm != -1)
      return table[nonterm][term];
    else
      return -1;
  }

  public int getCell(int x, TokenTuple t) {
    int y = getNonTerminalIndex(t);
    return table[y][x];
  }

  public int getWidth() {
    return terms.size();
  }

  public String getTerminal(int i) {
    return terms.get(i);
  }
}
