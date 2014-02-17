package parser;

import scanner.Scanner;
import scanner.TokenTuple;
import java.util.Stack;

public class Parser 
{
	private Stack<TokenTuple> parsingStack;
	private ParsingTable parsingTable;
	private Rule[] ruleTable;
	private Scanner scanner;
	private boolean debugFlag;
	private boolean isLegal;
	
	public Parser(Scanner scanner, String tableFileName)
	{
		isLegal = true;
		debugFlag = false;
		this.scanner = scanner;
		parsingTable = new ParsingTable(tableFileName);
		ruleTable = new Rule[78];
		parsingStack.push(new TokenTuple("EXIT", "$"));
		parsingStack.push(new TokenTuple("NONTERM", "<tiger-program>"));
		populateRuleTable();
	}
	
	public void parse(TokenTuple t)
	{
		//Print out the token type to the console (DEBUG ONLY)
		if(debugFlag)
			System.out.println(t.getType());
		
		//Check if the top of the stack is a nonterminal and if so, find the best fit rule for recursive descent
		if(parsingStack.peek().getType().equals("NONTERM"))
		{
			int rule = -1;
			if((rule = parsingTable.getCell(scanner.peekAtNextToken(), parsingStack.peek())) != -1)
			{
				//Replace the nonterminal with its recursive alternative
				parsingStack.pop();
				push(ruleTable[rule]);
			}
			else
			{
				//Record the error (syntactical error)
				System.out.println("Error on line ");
			}
		}
		else if(parsingStack.peek().getType().equals("EXIT"))
		{
			//Reached the end of the file
			isLegal = true;
		}
		else
		{
			if(t.equals(parsingStack.peek()))
			{
				parsingStack.pop();
			}
			else
			{
				//Record the error (syntactical Error)
				System.out.println("Error on line ");
			}
		}
	}
	
	public boolean isLegal()
	{
		return isLegal;
	}
	
	private void push(Rule rule)
	{
		for(int i = 0; i < rule.getLength(); i++)
		{
			parsingStack.push(rule.tokens[i]);
		}
	}
	
	private void populateRuleTable()
	{
		ruleTable[0] = Rule.determineFrom("");
	}
}
