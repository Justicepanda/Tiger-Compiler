package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;

import frontend.FrontEnd;
import scanner.TokenDfa;
import scanner.TokenDfaBuilder;
import scanner.Scanner;
import parser.Parser;

public class Main 
{	
	public static void main(String[] args)
	{	
		Scanner scanner = new Scanner((TokenDfa) new TokenDfaBuilder().buildFrom("TokenDFA.csv"));
		Parser parser = new Parser(scanner, "ParsingTable.csv");
		FrontEnd frontend = new FrontEnd(scanner, parser);
		
		boolean debugFlag = false;
		String filename = "";
		
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-d"))
				debugFlag = true;
			else
				filename = args[i];
		}
		if (args.length > 2 || (args.length > 1 && !debugFlag))
			System.err.println("Tiger-Compiler: Invalid arguments.");
					
		
		try
		{
			if(frontend.compile(filename, debugFlag))
			{
				System.out.println("\nTiger-Compiler: Parse Successful!");
			}
		}
		catch(Exception e)
		{
			System.err.println("Tiger-Compiler: Invalid filename.");
		}
	}
}
