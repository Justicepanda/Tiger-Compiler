package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.Type;

public class Expression extends ParserRule 
{
	private Type type;
	private Integer value;
	
	public Expression(Scanner scanner)
	{
		super(scanner);
	}

	@Override
	public void parse()
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("MINUS")) 
		{
			matchTerminal("MINUS");
			Expression expression = new Expression(scanner);
			matchNonTerminal(expression);
			type = expression.getType();
		}
		else 
		{
			AndOrTerm andOrTerm = new AndOrTerm(scanner);
			matchNonTerminal(andOrTerm);
			type = andOrTerm.getType();
		}
	}

	@Override
	public String getLabel() 
	{
		return "<expr>";
	}

	@Override
	public Type getType() 
	{
		return type;
	}
	
	public Integer getValue()
	{
		return value;
	}
}
