package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import scanner.Scanner;
import symboltable.*;
import symboltable.Type;

public class AddTerm extends ParserRule 
{
	private MultTerm multTerm;
	private AddTerm2 addTerm2;

	public AddTerm(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		matchNonTerminal(multTerm = new MultTerm(scanner));
		matchNonTerminal(addTerm2 = new AddTerm2(scanner));
	}

	@Override
	public String getLabel() 
	{
		return "<add-term>";
	}

	@Override
	public Type getType() 
	{
		if(addTerm2 != null && addTerm2.getType() != null && multTerm != null && multTerm.getType() != null)
		{
			if(multTerm.getType().isOfSameType(addTerm2.getType()))
				return multTerm.getType();
			throw new SemanticTypeException(lineNumber);
		}
		else if(multTerm != null && multTerm.getType() != null && addTerm2.getType() == null)
			return multTerm.getType();
		else if(addTerm2 != null && addTerm2.getType() != null && multTerm.getType() == null)
			return addTerm2.getType();
		return null;
	}

}
