package compiler;

import parser.ParserException;
import scanner.LexicalException;
import utilities.NormalFileScraper;
import parser.Parser;
import scanner.Scanner;

public class Compiler {

  public static Compiler debug(Scanner scanner, Parser parser) {
    return new DebugCompiler(scanner, parser);
  }

  public static Compiler normal(Scanner scanner, Parser parser) {
    return new Compiler(scanner, parser);
  }

  private final Scanner scanner;
  private final Parser parser;
  private boolean noErrorEncountered;
  private boolean parserErrorEncountered;
  String message;

  Compiler(Scanner scanner, Parser parser) {
    this.scanner = scanner;
    this.parser = parser;
  }

  public void compile(String filename)  {
    noErrorEncountered = true;
    scanner.reset();
    parser.reset();
    init(filename);
    while (scanner.hasMoreTokens() && !parserErrorEncountered)
      compileNextToken();
    addSuccessMessage();
  }

  public String getMessage() {
    return message;
  }

  public String getParseTreePrintout() {
    return parser.printTree();
  }

  private void init(String filename) {
    message = "";
    scanner.scan(getLinesFromFile(filename));
  }

  private String[] getLinesFromFile(String filename) {
    NormalFileScraper scraper = new NormalFileScraper();
    return scraper.read(filename);
  }

  private void compileNextToken() {
    TokenTuple token = scan();
    if (token != null && noErrorEncountered)
      parseToken(token);
  }

  TokenTuple scan() {
    TokenTuple token = null;
    try {
      token = scanner.getNextToken();
    } catch (LexicalException e) {
      handleScannerError(e);
    }
    return token;
  }

  void handleScannerError(LexicalException e) {
    noErrorEncountered = false;
    message += e.getMessage() + "\n";
  }

  private void parseToken(TokenTuple token) {
    try {
      parser.parse(token);
    } catch (ParserException e) {
      handleParserError(e);
    }
  }

  void handleParserError(ParserException e) {
    message += "Parsing error " + scanner.getLineInfo() + " <-- " + e.getMessage();
    noErrorEncountered = false;
    parserErrorEncountered = true;
  }

  void addSuccessMessage() {
    if (noErrorEncountered)
      message += "Tiger-Compiler: Compilation successful!\n";
    else
      message += "Tiger-Compiler: Compilation unsuccessful.\n";
  }
}
