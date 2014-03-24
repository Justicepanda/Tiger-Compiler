package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;
import scanner.Scanner;

public class AndOrTerm extends ParserRule 
{
	private IneqTerm ineqTerm;
	private AndOrTerm2 andOrTerm2;
	
	public AndOrTerm(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		matchNonTerminal(ineqTerm = new IneqTerm(scanner));
		matchNonTerminal(andOrTerm2 = new AndOrTerm2(scanner));
	}

	@Override
	public String getLabel() 
	{
		return "<and-or-term>";
	}

	@Override
	public Type getType() 
	{
		if(andOrTerm2.getType() != null && ineqTerm != null && ineqTerm.getType() != null)
		{
			if(ineqTerm.getType().isOfSameType(andOrTerm2.getType()))
				return ineqTerm.getType();
			throw new SemanticTypeException(lineNumber);
		}
		else if(ineqTerm != null && ineqTerm.getType() != null && andOrTerm2.getType() == null)
			return ineqTerm.getType();
		else if(andOrTerm2 != null && andOrTerm2.getType() != null && ineqTerm.getType() == null)
			return ineqTerm.getType();
		return null;
	}
}
