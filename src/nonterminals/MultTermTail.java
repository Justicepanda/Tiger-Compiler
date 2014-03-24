package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

public class MultTermTail extends ParserRule
{
	private LValue lValue;
	private MultTerm2 multTerm2;
	
	public MultTermTail(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		matchNonTerminal(lValue = new LValue(scanner));
		matchNonTerminal(multTerm2 = new MultTerm2(scanner));
	}

	@Override
	public String getLabel() 
	{
		return "<mult-term-tail>";
	}

	@Override
	public Type getType() 
	{
		return multTerm2.getType();
	}
}
