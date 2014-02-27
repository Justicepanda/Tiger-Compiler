package compiler;

import parser.ParserException;
import scanner.LexicalException;
import utilities.NormalFileScraper;
import parser.Parser;
import scanner.Scanner;

public class Compiler {
  private final Scanner scanner;
  private final Parser parser;
  private final NormalFileScraper scraper;
  private boolean success;

  public Compiler(Scanner scanner, Parser parser) {
    this.scanner = scanner;
    this.parser = parser;
    scraper = new NormalFileScraper();
    success = true;
  }

  public boolean compile(String filename)  {
    String[] lines = scraper.read(filename);
    scanner.scan(lines);
    TokenTuple token;
    while (scanner.hasMoreTokens()) {
      token = null;
      try {
        token = scanner.getNextToken();
      } catch (LexicalException e) {
        success = false;
        System.err.println(e.getMessage());
      }

      if (token != null && success)
        try {
          parser.parse(token);
        } catch (ParserException e) {
          System.err.println(e.getMessage());
          success = false;
          break;
        }
    }
    return success;
  }
}
