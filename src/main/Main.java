package main;

import frontend.FrontEnd;
import scanner.TokenDfa;
import scanner.TokenDfaBuilder;
import scanner.Scanner;
import parser.Parser;

public class Main 
{	
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner((TokenDfa) new TokenDfaBuilder().buildFrom("./assets/TokenDFA.csv"));
		Parser parser = new Parser(scanner, "./assets/ParsingTable.csv");
		FrontEnd frontend = new FrontEnd(scanner, parser);
		
		if(frontend.compile(args[0]))
		{
			System.out.println("Parse Successful!");
		}
	}
}
