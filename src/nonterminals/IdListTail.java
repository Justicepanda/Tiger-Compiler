package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

import java.util.ArrayList;
import java.util.List;

public class IdListTail extends ParserRule 
{
	private IdListTail idListTail;
	private String id;

	public IdListTail(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("COMMA")) 
		{
			idListTail = new IdListTail(scanner);
			matchTerminal("COMMA");
			id = peekTokenValue();
			matchTerminal("ID");
			matchNonTerminal(idListTail);
		}
	}

	@Override
	public String getLabel() 
	{
		return "<id-list-tail>";
	}

	public List<String> getIds() 
	{
		if (id == null)
			return null;
		List<String> listSoFar = idListTail.getIds();
		if (listSoFar == null)
			listSoFar = new ArrayList<String>();
		listSoFar.add(id);
		return listSoFar;
	}

	@Override
	public Type getType() 
	{
		return null;
	}
}
