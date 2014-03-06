package compiler;

import parser.Parser;
import parser.ParserException;
import scanner.LexicalException;
import scanner.Scanner;

class DebugCompiler extends Compiler {
  private boolean lineStarted;

  DebugCompiler(Scanner scanner, Parser parser) {
    super(scanner, parser);
  }

  /**
   * Overrides scan() to append each scanned token to
   * the compiler message.
   *
   * @return The scanned Token.
   */
  @Override
  protected TokenTuple scan() {
    TokenTuple t = super.scan();
    if (t != null)
      addTokenToMessage(t);
    return t;
  }

  private void addTokenToMessage(TokenTuple t) {
    if (lineStarted)
      message += " ";
    message += t.getType();
    lineStarted = true;
  }

  @Override
  void handleScannerError(LexicalException e) {
    message += "\n";
    lineStarted = false;
    super.handleScannerError(e);
  }

  @Override
  void handleParserError(ParserException e) {
    message += "\n";
    lineStarted = false;
    super.handleParserError(e);
  }

  @Override
  void addSuccessMessage() {
    message += "\n";
    lineStarted = false;
    super.addSuccessMessage();
  }
}
