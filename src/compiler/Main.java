package compiler;

import scanner.TokenDfa;
import scanner.TokenDfaBuilder;
import scanner.Scanner;
import parser.TigerParser;

class Main 
{
	private static boolean debugFlag;
	private static String filename;
	private static Compiler compiler;

	/**
	 * Compiles a file found in the command arguments. If '-d' is given as an
	 * argument then the compiler will print out tokens found in the compiled
	 * file.
	 * 
	 * @param args
	 *            Filename and optional debug flag.
	 */
	public static void main(String[] args) 
	{
		debugFlag = false;
		filename = "";
		parseArgs(args);

		if (invalidArguments(args)) 
		{
			System.err.println("Tiger-Compiler: Invalid arguments.");
			return;
		}

		initCompiler();
		compiler.compile(filename);
	}

	private static void parseArgs(String[] args) 
	{
		for (String arg : args) 
		{
			if (arg.equals("-d"))
				debugFlag = true;
			else
				filename = arg;
		}
	}

	private static boolean invalidArguments(String[] args) 
	{
		return args.length > 2 || (args.length > 1 && !debugFlag);
	}

	private static void initCompiler() 
	{
		Scanner scanner = new Scanner(
				(TokenDfa) new TokenDfaBuilder().buildFrom("TokenDFA.csv"));
		TigerParser parser = new TigerParser(scanner);
    if (debugFlag)
      parser.setDebug();
		compiler = new Compiler(scanner, parser);
	}
}
