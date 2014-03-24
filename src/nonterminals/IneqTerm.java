package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import scanner.Scanner;
import symboltable.Type;

public class IneqTerm extends ParserRule 
{
	private AddTerm addTerm;
	private IneqTerm2 ineqTerm2;
	
	public IneqTerm(Scanner scanner)
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		matchNonTerminal(addTerm = new AddTerm(scanner));
		matchNonTerminal(ineqTerm2 = new IneqTerm2(scanner));
	}

	@Override
	public String getLabel() 
	{
		return "<ineq-term>";
	}

	@Override
	public Type getType() 
	{
		if(ineqTerm2 != null && ineqTerm2.getType() != null && addTerm != null && addTerm.getType() != null)
		{
			if(addTerm.getType().isOfSameType(ineqTerm2.getType()))
				return addTerm.getType();
			throw new SemanticTypeException(lineNumber);
		}
		else if(addTerm != null && addTerm.getType() != null && ineqTerm2.getType() == null)
			return addTerm.getType();
		else if(ineqTerm2 != null && ineqTerm2.getType() != null && addTerm.getType() == null)
			return ineqTerm2.getType();
		return null;
	}
}
