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
  protected String message;

  protected Compiler(Scanner scanner, Parser parser) {
    this.scanner = scanner;
    this.parser = parser;
    noErrorEncountered = true;
  }

  public String compile(String filename)  {
    message = "";
    scanner.scan(getLinesFromFile(filename));
    while (scanner.hasMoreTokens() && !parserErrorEncountered)
      compileNextToken();
    addSuccessMessage();
    return message;
  }

  protected void addSuccessMessage() {
    if (noErrorEncountered)
      message += "Tiger-Compiler: Compilation successful!\n";
    else
      message += "Tiger-Compiler: Compilation unsuccessful.\n";
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

  protected TokenTuple scan() {
    TokenTuple token = null;
    try {
      token = scanner.getNextToken();
    } catch (LexicalException e) {
      handleScannerError(e);
    }
    return token;
  }

  protected void handleScannerError(LexicalException e) {
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

  protected void handleParserError(ParserException e) {
    message += "Parsing error " +
            scanner.getLineInfo() +
            " <-- " +
            e.getMessage();
    noErrorEncountered = false;
    parserErrorEncountered = true;
  }
}

class DebugCompiler extends Compiler {
  private boolean lineStarted;

  public DebugCompiler(Scanner scanner, Parser parser) {
    super(scanner, parser);
  }

  @Override
  protected TokenTuple scan() {
    TokenTuple t = super.scan();
    if (t != null) {
      if (lineStarted)
        message += " ";
      message += t.getType();
      lineStarted = true;
    }
    return t;
  }

  @Override
  protected void handleScannerError(LexicalException e) {
    message += "\n";
    lineStarted = false;
    super.handleScannerError(e);
  }

  @Override
  protected void handleParserError(ParserException e) {
    message += "\n";
    lineStarted = false;
    super.handleParserError(e);
  }

  @Override
  protected void addSuccessMessage() {
    message += "\n";
    lineStarted = false;
    super.addSuccessMessage();
  }
}
