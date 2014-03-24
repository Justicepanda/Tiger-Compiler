package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

public class MultOp extends ParserRule
{	
	public MultOp(Scanner scanner)
	{
		super(scanner);
	}

	@Override
	public void parse()
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("MULT")) 
		{
			matchTerminal("MULT");
		} 
		else
		{
			matchTerminal("DIV");
		}
	}

	@Override
	public String getLabel()
	{
		return "<mult-op>";
	}

	@Override
	public Type getType()
	{
		return null;
	}

}
