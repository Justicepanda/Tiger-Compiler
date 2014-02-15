package dfabuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileScraper {
  private BufferedReader br;
  private List<String> lineList;
  private String filename;

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

  private void initBufferedReader() {
    try {
      br = new BufferedReader((new FileReader(new File(filename))));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

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
