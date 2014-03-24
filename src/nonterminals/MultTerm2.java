package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import scanner.Scanner;
import symboltable.Type;

public class MultTerm2 extends ParserRule 
{
	private Factor factor;
	private MultTerm2 multTerm2;
	
	public MultTerm2(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("MULT") || peekTypeMatches("DIV")) 
		{
			matchNonTerminal(new MultOp(scanner));
			matchNonTerminal(factor = new Factor(scanner));
			matchNonTerminal(multTerm2 = new MultTerm2(scanner));
		}
	}

	@Override
	public String getLabel() 
	{
		return "<mult-term2>";
	}

	@Override
	public Type getType()
	{
		if(multTerm2 != null && multTerm2.getType() != null && factor != null && factor.getType() != null)
		{
				if(factor.getType().isOfSameType(multTerm2.getType()))
					return factor.getType();
				throw new SemanticTypeException(lineNumber);
		}
		else if(factor != null && factor.getType() != null && multTerm2.getType() == null)
			return factor.getType();
		else if(multTerm2 != null && multTerm2.getType() != null && factor.getType() == null)
			return multTerm2.getType();
		return null;
	}
}
