package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Type;
import scanner.Scanner;

public class AddTerm2 extends ParserRule 
{
	private MultTerm multTerm;
	private AddTerm2 addTerm2;
	
	public AddTerm2(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() 
	{	
		lineNumber = scanner.getLineNum();
	    if (peekTypeMatches("PLUS") || peekTypeMatches("MINUS")) 
	    {
			matchNonTerminal(new AddOp(scanner));
			matchNonTerminal(multTerm = new MultTerm(scanner));
			matchNonTerminal(addTerm2 = new AddTerm2(scanner));
	    }
	}

	@Override
	public String getLabel() 
	{
	  return "<add-term2>";
	}
  
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
