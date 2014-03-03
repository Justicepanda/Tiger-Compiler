package compiler;

import scanner.TokenDfa;
import scanner.TokenDfaBuilder;
import scanner.Scanner;
import parser.Parser;

class Main {
  private static boolean debugFlag;
  private static String filename;
  private static Compiler compiler;

  public static void main(String[] args) {
    debugFlag = false;
    filename = "";
    parseArgs(args);

    if (invalidArguments(args)) {
      System.err.println("Tiger-Compiler: Invalid arguments.");
      return;
    }

    initCompiler();
    compiler.compile(filename);
    System.out.println(compiler.getMessage());
	}

  private static void parseArgs(String[] args) {
    for (String arg : args) {
      if (arg.equals("-d"))
        debugFlag = true;
      else
        filename = arg;
    }
  }

  private static boolean invalidArguments(String[] args) {
    return args.length > 2 || (args.length > 1 && !debugFlag);
  }

  private static void initCompiler() {
    Scanner scanner = new Scanner((TokenDfa) new TokenDfaBuilder().buildFrom("TokenDFA.csv"));
    Parser parser = new Parser("ParsingTable.csv", "GrammarRules");
    if (debugFlag)
      compiler = Compiler.debug(scanner, parser);
    else
      compiler = Compiler.normal(scanner, parser);
  }
}
