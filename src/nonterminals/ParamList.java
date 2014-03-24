package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Argument;
import symboltable.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParamList extends ParserRule 
{
	private ParamListTail paramListTail;
	private Param param;

	public ParamList(Scanner scanner) 
	{
		super(scanner);
		paramListTail = new ParamListTail(scanner);
		param = new Param(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("ID")) {
			matchNonTerminal(param);
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
		List<Argument> listSoFar = paramListTail.getArguments();
		if (listSoFar == null)
			listSoFar = new ArrayList<Argument>();
		listSoFar.add(param.getArgument());
		Collections.reverse(listSoFar);
		return listSoFar;
	}

	@Override
	public Type getType() 
	{
		return null;
	}
}
