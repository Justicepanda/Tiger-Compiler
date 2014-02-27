package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class NormalFileScraper extends FileScraper {

  @Override
  protected void initBufferedReader() {
    try {
      br = new BufferedReader((new FileReader(new File(filename))));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
