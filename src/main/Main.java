package main;

import compiler.Compiler;
import scanner.TokenDfa;
import scanner.TokenDfaBuilder;
import scanner.Scanner;
import parser.Parser;

class Main
{	
	public static void main(String[] args)
	{
    boolean debugFlag = false;
    String filename = "";
    for (String arg : args) {
      if (arg.equals("-d"))
        debugFlag = true;
      else
        filename = arg;
    }

    if (args.length > 2 || (args.length > 1 && !debugFlag)) {
      System.err.println("Tiger-Compiler: Invalid arguments.");
      return;
    }

    Scanner scanner = new Scanner((TokenDfa) new TokenDfaBuilder().buildFrom("TokenDFA.csv"));
    Parser parser = new Parser("ParsingTable.csv", "GrammarRules");

    Compiler compiler;
    if (debugFlag)
      compiler = Compiler.debug(scanner, parser);
    else
      compiler = Compiler.normal(scanner, parser);

    System.out.println(compiler.compile(filename));

	}
}
