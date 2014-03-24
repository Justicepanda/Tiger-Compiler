package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import scanner.Scanner;
import symboltable.Type;

public class IneqTermTail extends ParserRule 
{
	private AddTermTail addTermTail;
	private IneqTerm2 ineqTerm2;
	
	public IneqTermTail(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		matchNonTerminal(addTermTail = new AddTermTail(scanner));
		matchNonTerminal(ineqTerm2 = new IneqTerm2(scanner));
	}

	@Override
	public String getLabel() 
	{
		return "<ineq-term-tail>";
	}

	@Override
	public Type getType() 
	{
		if(ineqTerm2 != null && ineqTerm2.getType() != null && addTermTail != null && addTermTail.getType() != null)
		{
			if(addTermTail.getType().isOfSameType(ineqTerm2.getType()))
				return addTermTail.getType();
			throw new SemanticTypeException(lineNumber);
		}
		else if(addTermTail != null && addTermTail.getType() != null && ineqTerm2.getType() == null)
			return addTermTail.getType();
		else if(ineqTerm2 != null && ineqTerm2.getType() != null && addTermTail.getType() == null)
			return ineqTerm2.getType();
		return null;
	}
}
