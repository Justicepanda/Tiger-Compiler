package utilities;

class CsvReader {
  private final StringSplitter ss;
  private String[] lines;
  private int currLine;

  public CsvReader() {
    ss = new StringSplitter(',');
    currLine = 1;
  }

  public void read(String filename) {
    lines = new ResourceFileScraper().read(filename);
  }

  public String[] getHeader() {
    return ss.split(lines[0]);
  }

  public String[] getLine() {
    currLine++;
    return ss.split(lines[currLine - 1]);
  }

  public boolean hasLine() {
    return currLine < lines.length;
  }
}

