package frontend;

import dfabuilder.NormalFileScraper;
import parser.Parser;
import scanner.Scanner;
import scanner.LexicalException;

public class FrontEnd 
{
  private final Scanner scanner;
  private final Parser parser;
  private final NormalFileScraper scraper;

  public FrontEnd(Scanner scanner, Parser parser) 
  {
    this.scanner = scanner;
    this.parser = parser;
    scraper = new NormalFileScraper();
  }

  public boolean compile(String filename, boolean debugFlag) 
  {
	String[] lines = scraper.read(filename);
    scanner.scan(lines);
    parser.parse(debugFlag);
    return parser.isLegal();
  }
}
