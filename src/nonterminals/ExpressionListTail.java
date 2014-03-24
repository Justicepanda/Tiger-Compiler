package nonterminals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

public class ExpressionListTail extends ParserRule 
{
	private ExpressionListTail expressionListTail;
	private Expression expr;
	
	public ExpressionListTail(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("COMMA")) 
		{
			matchTerminal("COMMA");
			expr = new Expression(scanner);
			matchNonTerminal(expr);
			expressionListTail = new ExpressionListTail(scanner);
			matchNonTerminal(expressionListTail);
		}
	}

	@Override
	public String getLabel() 
	{
		return "<expr-list-tail>";
	}
	
	public List<Expression> getExpressions() 
	{
		if(expr == null)
			return null;
		List<Expression> listSoFar = expressionListTail.getExpressions();
		if (listSoFar == null)
			listSoFar = new ArrayList<Expression>();
		listSoFar.add(expr);
		return listSoFar;
	}

	@Override
	public Type getType() 
	{
		return null;
	}
}
