package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

public class StatId extends ParserRule 
{
	private Type type;
	private boolean isFunction;
	private ExpressionList expressions;
		
	public StatId(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("LPAREN"))
		{
			isFunction = true;
			matchTerminal("LPAREN");
   			matchNonTerminal(expressions = new ExpressionList(scanner));
			matchTerminal("RPAREN");
		} 
		else
		{
			matchNonTerminal(new LValue(scanner));
			matchTerminal("ASSIGN");
			StatIdTail statIdTail;
			matchNonTerminal(statIdTail = new StatIdTail(scanner));
			type = statIdTail.getType();
		}
	}

	@Override
	public String getLabel() 
	{
		return "<stat-id>";
	}

	@Override
	public Type getType() 
	{
		return type;
	}
	
	public boolean isFunction()
	{
		return isFunction;
	}
	
	public ExpressionList getParameters()
	{
		return expressions;
	}
}
