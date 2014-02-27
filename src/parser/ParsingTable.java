package parser;

import utilities.SvReader;
import compiler.TokenTuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ParsingTable {
  private List<List<Integer>> table;
  private final SvReader fileReader;
  private final List<String> nonTerminals;
  private List<String> terminals;

  public ParsingTable(String fileName) {
    fileReader = new SvReader(',');
    fileReader.read(fileName);
    nonTerminals = new ArrayList<String>();
    initTerminals();
    initTable();
  }

  private void initTerminals() {
    String[] header = fileReader.getHeader();
    header = Arrays.copyOfRange(header, 2, header.length);
    terminals = Arrays.asList(header);
  }

  private void initTable() {
    table = new ArrayList<List<Integer>>();
    while (fileReader.hasLine())
      addLine();
  }

  private void addLine() {
    String[] cells = fileReader.getLine();
    nonTerminals.add(cells[0]);
    List<Integer> line = new ArrayList<Integer>();
    for (int i = 0; i < terminals.size(); i++)
      addCell(cells[i + 1], line);
    table.add(line);
  }

  private void addCell(String cell, List<Integer> line) {
    try {
      line.add(Integer.parseInt(cell));
    } catch (Exception ex) {
      line.add(0);
    }
  }

  public int getCell(int x, TokenTuple t) {
    int y = getNonTerminalIndex(t);
    return table.get(y).get(x);
  }

  public int getCell(TokenTuple terminal, TokenTuple nonTerminal) {
    int term = getTerminalIndex(terminal);
    int nonTerm = getNonTerminalIndex(nonTerminal);
    if (term != -1 && nonTerm != -1)
      return table.get(nonTerm).get(term);
    else
      return -1;
  }

  private int getTerminalIndex(TokenTuple terminal) {
    for (int i = 0; i < terminals.size(); i++)
      if (terminalMatches(terminal, terminals.get(i)))
        return i;
    return -1;
  }

  private boolean terminalMatches(TokenTuple terminal, String toCheck) {
    return toCheck.equals(terminal.getToken())
            || toCheck.equalsIgnoreCase(terminal.toString());
  }

  private int getNonTerminalIndex(TokenTuple nonTerminal) {
    for (int i = 0; i < nonTerminals.size(); i++)
      if (nonTerminals.get(i).equals(nonTerminal.getToken()))
        return i;
    return -1;
  }

  public int getWidth() {
    return terminals.size();
  }

  public String getTerminal(int i) {
    return terminals.get(i);
  }
}
