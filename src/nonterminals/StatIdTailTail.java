package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

public class StatIdTailTail extends ParserRule 
{
	private ExpressionList parameters;
	private Type type;
	
	public StatIdTailTail(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("LPAREN")) 
		{
			
			matchTerminal("LPAREN");
			matchNonTerminal(parameters = new ExpressionList(scanner));
			matchTerminal("RPAREN");
		} 
		else
		{
			AndOrTermTail andOrTermTail;
			matchNonTerminal(andOrTermTail = new AndOrTermTail(scanner));
			type = andOrTermTail.getType();
		}
	}

	@Override
	public String getLabel() 
	{
		return "<stat-id-tail-tail>";
	}

	@Override
	public Type getType() 
	{
		return type;
	}
	
	public ExpressionList getParameters()
	{
		return parameters;
	}

}
