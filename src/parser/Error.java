package parser;

import compiler.TokenTuple;

class Error {
  private final String errorString;

  public Error(String error, int line, TokenTuple token) {
    errorString = error + " at line " + line + " at the token " + token;
  }

  public String getError() {
    return errorString;
  }
}