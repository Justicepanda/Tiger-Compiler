package nonterminals;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

public class LValue extends ParserRule 
{
	private Expression expression;
	private LValue lValue;
	
	public LValue(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("LBRACK")) 
		{
			matchTerminal("LBRACK");
			expression = new Expression(scanner);
			matchNonTerminal(expression);
			matchTerminal("RBRACK");
			lValue = new LValue(scanner);
		}
	}

	@Override
	public String getLabel() 
	{
		return "<lvalue>";
	}

	@Override
	public Type getType() 
	{
		return null;
	}
	
	public List<Integer> getDimensions()
	{
		List<Integer> listSoFar = lValue.getDimensions();
		if(listSoFar == null)
			listSoFar = new ArrayList<Integer>();
		listSoFar.add(expression.getValue());
		Collections.reverse(listSoFar);
		return listSoFar;
	}
}
