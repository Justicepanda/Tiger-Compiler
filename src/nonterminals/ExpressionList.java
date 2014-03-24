package nonterminals;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

public class ExpressionList extends ParserRule 
{
	private ExpressionListTail expressionListTail;
	private Expression expr;
	
	public ExpressionList(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("ID") || peekTypeMatches("LPAREN")
				|| peekTypeMatches("INTLIT") || peekTypeMatches("STRLIT")
				|| peekTypeMatches("NIL")) 
		{
			matchNonTerminal(expr = new Expression(scanner));
			matchNonTerminal(expressionListTail = new ExpressionListTail(scanner));
		}
	}

	@Override
	public String getLabel() 
	{
		return "<expr-list>";
	}

	public List<Expression> getExpressions() 
	{
		List<Expression> listSoFar = expressionListTail.getExpressions();
		if (listSoFar == null)
			listSoFar = new ArrayList<Expression>();
		listSoFar.add(expr);
		Collections.reverse(listSoFar);
		return listSoFar;
	}

	@Override
	public Type getType() 
	{
		return null;
	}
}
