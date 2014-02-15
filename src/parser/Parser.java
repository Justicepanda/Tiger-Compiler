package parser;

import scanner.Scanner;
import scanner.TokenTuple;
import java.util.Stack;

public class Parser 
{
	private Stack<TokenTuple> parsingStack;
	private ParsingTable parsingTable;
	private boolean isLegal;
	
	public Parser(Scanner scanner)
	{
		isLegal = true;
	}
	
	public void parse(TokenTuple t)
	{
		
	}
	
	public boolean isLegal()
	{
		return isLegal;
	}
}
