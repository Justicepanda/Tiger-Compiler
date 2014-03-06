import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class TestReader {
  private BufferedReader br;

  public String read(String filename) {
    getBufferedReader(filename);
    String content = readFile();
    closeBufferedReader();
    return content;
  }

  private void getBufferedReader(String filename) {
    try {
      br = new BufferedReader(new FileReader(filename));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private String readFile() {
    String content = "";
    String line;
    do {
      line = getNextLine();
      if (line != null)
        content += line + "\n";
    } while (line != null);
    return content;
  }

  private String getNextLine() {
    try {
      return br.readLine();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private void closeBufferedReader() {
    try {
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
