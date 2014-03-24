package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;
import scanner.Scanner;

public class IneqTerm2 extends ParserRule 
{
	private AddTerm addTerm;
	private IneqTerm2 ineqTerm2;
	
	public IneqTerm2(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("NEQ") || peekTypeMatches("EQ")
				|| peekTypeMatches("LESSER") || peekTypeMatches("GREATER")
				|| peekTypeMatches("LESSEREQ") || peekTypeMatches("GREATEREQ")) 
		{
			matchNonTerminal(new Ineq(scanner));
			matchNonTerminal(addTerm = new AddTerm(scanner));
			matchNonTerminal(ineqTerm2 = new IneqTerm2(scanner));
		}
	}

	@Override
	public String getLabel() 
	{
		return "<ineq-term-2>";
	}

	@Override
	public Type getType() 
	{
		if(ineqTerm2 != null && ineqTerm2.getType() != null && addTerm != null && ineqTerm2.getType() != null)
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
