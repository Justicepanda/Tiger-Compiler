package nonterminals;

import java.util.ArrayList;

import parser.ParserRule;
import parser.SemanticTypeException;
import scanner.Scanner;
import symboltable.Argument;
import symboltable.Type;

public class StatIdTail extends ParserRule 
{
	private Type type;
	
	public StatIdTail(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("ID")) 
		{
			String id = scanner.peekToken().getToken();
			matchTerminal("ID");
			StatIdTailTail statIdTailTail;
			matchNonTerminal(statIdTailTail = new StatIdTailTail(scanner));
			if(statIdTailTail.getType() == null)
			{
				if(statIdTailTail.getParameters() != null)
				{
					if(symbolTable.getFunction(id) != null)
					{
						type = symbolTable.getFunction(id).getReturnType();
						if(!statIdTailTail.getType().isOfSameType(type))
						{
							throw new SemanticTypeException(statIdTailTail.getLineNumber());
						}
						ArrayList<Argument> args = symbolTable.getFunction(id).getArguments();
						//TODO - Check it's parameters types to make sure they match the declared parameter types
						for(int i = 0; i < statIdTailTail.getParameters().getExpressions().size(); i++)
						{
							if(!statIdTailTail.getParameters().getExpressions().get(i).getType().isOfSameType(args.get(i).getType()))
							{
								throw new SemanticTypeException(statIdTailTail.getLineNumber());
							}
						}
					}
				}
				else
				{
					if(symbolTable.getVariable(id) != null)
						type = symbolTable.getVariable(id).getType();
				}
			}
			else
			{
				type = statIdTailTail.getType();
			}
		} 
		else 
		{
			Expression expression;
			matchNonTerminal(expression = new Expression(scanner));
			type = expression.getType();
		}
	}

	@Override
	public String getLabel()
	{
		return "<stat-id-tail>";
	}

	@Override
	public Type getType()
	{
		return type;
	}

}
