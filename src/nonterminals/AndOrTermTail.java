package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import scanner.Scanner;
import symboltable.Type;

public class AndOrTermTail extends ParserRule 
{
	private IneqTermTail ineqTermTail;
	private AndOrTerm2 andOrTerm2;
	
	public AndOrTermTail(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		matchNonTerminal(ineqTermTail = new IneqTermTail(scanner));
		matchNonTerminal(andOrTerm2 = new AndOrTerm2(scanner));
	}

	@Override
	public String getLabel()
	{
		return "<and-or-term-tail>";
	}

	@Override
	public Type getType() 
	{
		if(andOrTerm2 != null && andOrTerm2.getType() != null && ineqTermTail != null && ineqTermTail.getType() != null)
		{
			if(ineqTermTail.getType().isOfSameType(andOrTerm2.getType()))
				return ineqTermTail.getType();
			throw new SemanticTypeException(lineNumber);
		}
		else if(ineqTermTail != null && ineqTermTail.getType() != null && andOrTerm2.getType() == null)
 			return ineqTermTail.getType();
		else if(andOrTerm2 != null && andOrTerm2.getType() != null && ineqTermTail.getType() == null)
			return andOrTerm2.getType();
		return null;
	}
}
