package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Argument;
import symboltable.Type;

import java.util.ArrayList;
import java.util.List;

public class ParamListTail extends ParserRule 
{
	private Param param;
	private ParamListTail paramListTail;

	public ParamListTail(Scanner scanner) 
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
			param = new Param(scanner);
			matchNonTerminal(param);
			paramListTail = new ParamListTail(scanner);
			matchNonTerminal(paramListTail);
		}
	}

	@Override
	public String getLabel() 
	{
		return "<param-list>";
	}

	public List<Argument> getArguments() 
	{
		if (param == null)
			return null;
		List<Argument> listSoFar = paramListTail.getArguments();
		if (listSoFar == null)
			listSoFar = new ArrayList<Argument>();
		listSoFar.add(param.getArgument());
		return listSoFar;
	}

	@Override
	public Type getType() 
	{
		return null;
	}
}
