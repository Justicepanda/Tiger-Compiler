package parser;

import scanner.LexicalException;
import scanner.Scanner;
import scanner.TokenTuple;
import dfabuilder.FileScraper;

import java.util.Stack;

public class Parser 
{
	private Stack<TokenTuple> parsingStack;
	private ParsingTable parsingTable;
	private Rule[] ruleTable;
	private Scanner scanner;
	private boolean debugFlag;
	private boolean isLegal;
	private String currentLine;
	private int currentLineNo;
	
	public Parser(Scanner scanner, String tableFileName)
	{
		//(DEBUG ONLY)
		debugFlag = true;
		
		currentLine = "";
		currentLineNo = 0;
		isLegal = true;
		this.scanner = scanner;
		parsingTable = new ParsingTable(tableFileName);
		parsingStack = new Stack<TokenTuple>();
		ruleTable = new Rule[92];
		parsingStack.push(new TokenTuple("EXIT", "$"));
		parsingStack.push(new TokenTuple("NONTERM", "<tiger-program>"));
		populateRuleTable();
	}
	
	public void parse(boolean debugFlag)
	{
		this.debugFlag = debugFlag;
		while (scanner.hasMoreTokens() && isLegal)
	    {
	    	try
	    	{
	    		TokenTuple t = scanner.getNextToken();
	    		//Print out the token type to the console (DEBUG ONLY)
	    		if(debugFlag && t != null)
	    			System.out.print(t.getType() + " ");
	    		
	    		if(scanner.isValid())
	    		{
		    		//Check if the top of the stack is a nonterminal and if so, find the best fit rule for recursive descent
					while(parsingStack.peek().getType().equals("NONTERM"))
					{
		    			int rule = 0;
		    			if((rule = parsingTable.getCell(t, parsingStack.peek())) != 0)
		    			{
		    				//Replace the nonterminal with its recursive alternative
		    				TokenTuple x = parsingStack.pop();
		    				push(ruleTable[rule - 1]);
		    				
		    				if(parsingStack.peek().getToken().equals("NULL"))
		    					parsingStack.pop();
		    			}
		    			else
		    			{
		    				//Record the error (syntactical error)
		    				System.out.println("\nParsing error (line " + (scanner.getLineHandler().getLineNo() + 1) + "): " + scanner.getLineHandler().getLineUpToCurrChar(t.getToken().length()) + " <-- " + HandleError(parsingStack.peek()));
		    				
		    				isLegal = false;
		    				return;
		    			}
					}
		    		
		    		if(parsingStack.peek().getType().equals("EXIT"))
		    		{
		    			//Reached the end of the file
		    			isLegal = true;
		    		}
		    		else if(t.getType().equals(parsingStack.peek().getType()))
					{
						parsingStack.pop();
					}
		    		else
		    		{
		    			System.out.println("\nParsing error (line " + (scanner.getLineHandler().getLineNo() + 1) + "): " + scanner.getLineHandler().getLineUpToCurrChar(t.getToken().length()) + " <-- \"" + t.getToken() + "\" is not a valid token. Expected \"" + parsingStack.peek().getToken() + "\".");
	    				isLegal = false;
		    		}
	    		}
	    	}
	    	catch(LexicalException e)
	    	{
	    		System.err.println(e.toString());
	    	}
	    }
	}
	
	public boolean isLegal()
	{
		return isLegal && scanner.isValid();
	}
	
	private void push(Rule rule)
	{
		for(int i = rule.getLength() - 1; i >= 0; i--)
		{
			parsingStack.push(rule.tokens[i]);
		}
	}
	
	public String HandleError(TokenTuple expected)
	{
		String returnValue = "expected ";
		
		for(int i = 0; i < parsingTable.getWidth() - 1; i++)
		{
			if(parsingTable.getCell(i, expected) != 0)
			{
				returnValue += "'" + parsingTable.getTerminals()[i] + "' or ";
			}
		}
		
		return returnValue.substring(0, returnValue.length() - 3);
	}
	
	private void populateRuleTable()
	{
		ruleTable[0] = Rule.determineFrom("let <declaration-segment> in <stat-seq> end");
		ruleTable[1] = Rule.determineFrom("<type-declaration-list> <var-declaration-list> <funct-declaration-list>");
		ruleTable[2] = Rule.determineFrom("NULL");
		ruleTable[3] = Rule.determineFrom("<type-declaration> <type-declaration-list>");
		ruleTable[4] = Rule.determineFrom("NULL");
		ruleTable[5] = Rule.determineFrom("<var-declaration> <var-declaration-list>");
		ruleTable[6] = Rule.determineFrom("NULL");
		ruleTable[7] = Rule.determineFrom("<funct-declaration> <funct-declaration-list>");
		ruleTable[8] = Rule.determineFrom("type id = <type> ;");
		ruleTable[9] = Rule.determineFrom("<type-id>");
		ruleTable[10] = Rule.determineFrom("array [ INTLIT ] of <type-id>");
		ruleTable[11] = Rule.determineFrom("int");
		ruleTable[12] = Rule.determineFrom("string");
		ruleTable[13] = Rule.determineFrom("id");
		ruleTable[14] = Rule.determineFrom("var <id-list> : <type-id> <optional-init> ;");
		ruleTable[15] = Rule.determineFrom("id <id-list-tail>");
		ruleTable[16] = Rule.determineFrom(", id <id-list-tail>");
		ruleTable[17] = Rule.determineFrom("NULL");
		ruleTable[18] = Rule.determineFrom("NULL");
		ruleTable[19] = Rule.determineFrom(":= <const>");
		ruleTable[20] = Rule.determineFrom("function id ( <param-list> ) <ret-type> begin <stat-seq> end ;");
		ruleTable[21] = Rule.determineFrom("NULL");
		ruleTable[22] = Rule.determineFrom("<param> <param-list-tail>");
		ruleTable[23] = Rule.determineFrom("NULL");
		ruleTable[24] = Rule.determineFrom(", <param> <param-list-tail>");
		ruleTable[25] = Rule.determineFrom("NULL");
		ruleTable[26] = Rule.determineFrom(": <type-id>");
		ruleTable[27] = Rule.determineFrom("id : <type-id>");
		//Skipped Rule 29 because it doesn't exist
		ruleTable[29] = Rule.determineFrom("<stat> <stat-seq>");
		ruleTable[30] = Rule.determineFrom("NULL");
		ruleTable[31] = Rule.determineFrom("id <stat-id> ;");
		ruleTable[32] = Rule.determineFrom("( <expr-list> )");
		ruleTable[33] = Rule.determineFrom("<lvalue> := <stat-id-tail>");
		ruleTable[34] = Rule.determineFrom("if <expr> then <stat-seq> <stat-tail>");
		ruleTable[35] = Rule.determineFrom("endif ;");
		ruleTable[36] = Rule.determineFrom("else <stat-seq> endif ;");
		ruleTable[37] = Rule.determineFrom("while <expr> do <stat-seq> enddo ;");
		ruleTable[38] = Rule.determineFrom("for id := <expr> to <expr> do <stat-seq> enddo ;");
		ruleTable[39] = Rule.determineFrom("break ;");
		ruleTable[40] = Rule.determineFrom("- <expr>");
		ruleTable[41] = Rule.determineFrom("<andorterm>");
		ruleTable[42] = Rule.determineFrom("<ineqterm> <andorterm2>");
		ruleTable[43] = Rule.determineFrom("<andorop> <ineqterm> <andorterm2>");
		ruleTable[44] = Rule.determineFrom("NULL");
		ruleTable[45] = Rule.determineFrom("|");
		ruleTable[46] = Rule.determineFrom("&");
		ruleTable[47] = Rule.determineFrom("<addterm> <ineqterm2>");
		ruleTable[48] = Rule.determineFrom("<ineq> <addterm> <ineqterm2>");
		ruleTable[49] = Rule.determineFrom("NULL");
		ruleTable[50] = Rule.determineFrom("<>");
		ruleTable[51] = Rule.determineFrom("=");
		ruleTable[52] = Rule.determineFrom("<");
		ruleTable[53] = Rule.determineFrom(">");
		ruleTable[54] = Rule.determineFrom("<=");
		ruleTable[55] = Rule.determineFrom("<multterm> <addterm2>");
		ruleTable[56] = Rule.determineFrom("<addop> <multterm> <addterm2>");
		ruleTable[57] = Rule.determineFrom("NULL");
		ruleTable[58] = Rule.determineFrom("+");
		ruleTable[59] = Rule.determineFrom("-");
		ruleTable[60] = Rule.determineFrom("<factor> <multterm2>");
		ruleTable[61] = Rule.determineFrom("<multop> <factor> <multterm2>");
		ruleTable[62] = Rule.determineFrom("NULL");
		ruleTable[63] = Rule.determineFrom("/");
		ruleTable[64] = Rule.determineFrom("*");
		ruleTable[65] = Rule.determineFrom("( <expr> )");
		ruleTable[66] = Rule.determineFrom("<const>");
		ruleTable[67] = Rule.determineFrom("<lvalue>");
		ruleTable[68] = Rule.determineFrom("INTLIT");
		ruleTable[69] = Rule.determineFrom("STRLIT");
		ruleTable[70] = Rule.determineFrom("nil");
		ruleTable[71] = Rule.determineFrom("NULL");
		ruleTable[72] = Rule.determineFrom("<expr> <expr-list-tail>");
		ruleTable[73] = Rule.determineFrom(", <expr> <expr-list-tail>");
		ruleTable[74] = Rule.determineFrom("NULL");
		ruleTable[75] = Rule.determineFrom("[ <expr> ] <lvalue>");
		ruleTable[76] = Rule.determineFrom("NULL");
		ruleTable[77] = Rule.determineFrom(">=");
		ruleTable[78] = Rule.determineFrom("!=");
		//Skipped Rule 80 because it doesn't exist
		ruleTable[80] = Rule.determineFrom("id <stat-id-tail-tail>");
		ruleTable[81] = Rule.determineFrom("<expr>");
		ruleTable[82] = Rule.determineFrom("( <expr-list> )");
		ruleTable[83] = Rule.determineFrom("<expr-tail>");
		ruleTable[84] = Rule.determineFrom("<andorterm-tail>");
		ruleTable[85] = Rule.determineFrom("<ineqterm-tail> <andorterm2>");
		ruleTable[86] = Rule.determineFrom("<addterm-tail> <ineqterm2>");
		ruleTable[87] = Rule.determineFrom("<multterm-tail> <addterm2>");
		ruleTable[88] = Rule.determineFrom("<factor-tail> <multterm2>");
		ruleTable[89] = Rule.determineFrom("id <factor-tail>");
		ruleTable[90] = Rule.determineFrom("- INTLIT");
		ruleTable[91] = Rule.determineFrom("return <expr> ;");
	}
}
