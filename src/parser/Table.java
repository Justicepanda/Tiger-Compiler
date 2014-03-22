package parser;

import utilities.SvReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Table {
  private final List<String> verticalHeader;
  private List<String> horizontalHeader;
  private List<List<Integer>> cells;

  Table(SvReader file) {
    initHorizontalHeader(file.getHeader());
    verticalHeader = new ArrayList<String>();
    initTable(file);
  }

  private void initHorizontalHeader(String[] header) {
    header = Arrays.copyOfRange(header, 2, header.length);
    horizontalHeader = Arrays.asList(header);
  }

  private void initTable(SvReader file) {
    cells = new ArrayList<List<Integer>>();
    while (file.hasLine())
      addLine(file.getLine());
  }

  private void addLine(String[] line) {
    verticalHeader.add(line[0]);
    List<Integer> row = new ArrayList<Integer>();
    for (int i = 0; i < horizontalHeader.size(); i++)
      addCell(line[i + 1], row);
    cells.add(row);
  }

  private void addCell(String cell, List<Integer> line) {
    try {
      line.add(Integer.parseInt(cell));
    } catch (Exception ex) {
      line.add(0);
    }
  }

  int getWidth() {
    return horizontalHeader.size();
  }

  int getValue(int row, int col) {
    return cells.get(row).get(col);
  }

  int getRow(String toFind) {
    for (int i = 0; i < verticalHeader.size(); i++)
      if (toFind.equalsIgnoreCase(verticalHeader.get(i)))
        return i;
    return -1;
  }

  int getCol(String token, String type) {
    for (int i = 0; i < horizontalHeader.size(); i++)
      if (token.equals(horizontalHeader.get(i)) || type.equalsIgnoreCase(horizontalHeader.get(i)))
        return i;
    return -1;
  }

  String getHorizontalHeader(int index) {
    return horizontalHeader.get(index);
  }
}
