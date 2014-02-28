package compiler;

import parser.Parser;
import parser.ParserException;
import scanner.LexicalException;
import scanner.Scanner;

class DebugCompiler extends Compiler {
  private boolean lineStarted;

  protected DebugCompiler(Scanner scanner, Parser parser) {
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
