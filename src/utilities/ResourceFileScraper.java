package utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceFileScraper extends FileScraper {

  @Override
  protected void initBufferedReader() {
    try {
      InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
      br = new BufferedReader(new InputStreamReader(inputStream));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
