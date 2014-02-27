package main;

import compiler.Compiler;
import parser.DebugParser;
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
    Parser parser;
    if (debugFlag)
      parser = new DebugParser(scanner, "ParsingTable.csv", "GrammarRules");
    else
      parser = new Parser(scanner, "ParsingTable.csv", "GrammarRules");

    Compiler frontend = new compiler.Compiler(scanner, parser);

		if(frontend.compile(filename))
			System.out.println("\nTiger-Compiler: Parse Successful!");
		else
			System.out.println("\nTiger-Compiler: Parse Unsuccessful.");
	}
}
