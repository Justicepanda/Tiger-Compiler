package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;
import scanner.Scanner;

public class AddTermTail extends ParserRule 
{
	private MultTermTail multTermTail;
	private AddTerm2 addTerm2;
	
	public AddTermTail(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		matchNonTerminal(multTermTail = new MultTermTail(scanner));
		matchNonTerminal(addTerm2 = new AddTerm2(scanner));
	}

	@Override
	public String getLabel() 
	{
		return "<add-term-tail>";
	}
	
	@Override
	public Type getType()
	{
		if(addTerm2 != null && addTerm2.getType() != null && multTermTail != null && multTermTail.getType() != null)
		{
			if(multTermTail.getType().isOfSameType(addTerm2.getType()))
				return multTermTail.getType();
			throw new SemanticTypeException(lineNumber);
		}
		else if(multTermTail != null && multTermTail.getType() != null && addTerm2.getType() == null)
			return multTermTail.getType();
		else if(addTerm2 != null && addTerm2.getType() != null && multTermTail.getType() == null)
			return addTerm2.getType();
		return null;
	}
}
