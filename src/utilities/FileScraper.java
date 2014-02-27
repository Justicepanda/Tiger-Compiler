package utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class FileScraper {
  BufferedReader br;
  private List<String> lineList;
  String filename;

  public String[] read(String filename) {
    this.filename = filename;
    initLineList();
    return convertListToArray();
  }

  private void initLineList() {
    initBufferedReader();
    lineList = new ArrayList<String>();
    populateLineList();
  }

  private String[] convertListToArray() {
    String[] lines = new String[lineList.size()];
    return lineList.toArray(lines);
  }

  protected abstract void initBufferedReader();

  private void populateLineList() {
    String line;
    try {
      while ((line = br.readLine()) != null)
        lineList.add(line);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
