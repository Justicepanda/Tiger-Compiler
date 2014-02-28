package parser;

import utilities.SvReader;
import compiler.TokenTuple;

class ParsingTable {
  private Table table;

  public ParsingTable(String fileName) {
    SvReader file = new SvReader(',');
    file.read(fileName);
    table = new Table(file);
  }

  public int findIntersection(TokenTuple terminal, TokenTuple nonTerminal) {
    int term = table.getCol(terminal.getToken(), terminal.getType());
    int nonTerm = table.getRow(nonTerminal.getToken());
    if (term != -1 && nonTerm != -1)
      return table.getValue(nonTerm, term);
    else
      return -1;
  }

  public String getOrSeparatedTerminals(TokenTuple expected) {
    String res = "";
    for (int i = 0; i < table.getWidth(); i++)
      if (ruleIsLegal(getCell(i, expected)))
        res += "'" + table.getHorizontalHeader(i) + "' or ";
    return res.substring(0, res.length() - 4);
  }

  private boolean ruleIsLegal(int ruleValue) {
    return ruleValue != 0;
  }

  private int getCell(int term, TokenTuple token) {
    int nonTerm = table.getRow(token.getToken());
    return table.getValue(nonTerm, term);
  }
}
