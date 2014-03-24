package nonterminals;

import java.util.ArrayList;

import parser.ParserRule;
import parser.SemanticTypeException;
import scanner.Scanner;
import symboltable.Argument;
import symboltable.Type;
import symboltable.Variable;

public class Stat extends ParserRule
{
	public Stat(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("RETURN")) 
		{
			matchTerminal("RETURN");
			matchNonTerminal(new Expression(scanner));
			matchTerminal("SEMI");
		} 
		else if (peekTypeMatches("ID")) 
		{
			String id = scanner.peekToken().getToken();
			matchTerminal("ID");
			StatId statId;
			matchNonTerminal(statId = new StatId(scanner));
			matchTerminal("SEMI");
			if(statId.isFunction())
			{
				if(symbolTable.getFunction(id) != null)
				{
					if(statId.getParameters() != null)
					{
						ArrayList<Argument> args = symbolTable.getFunction(id).getArguments();
						for(int i = 0; i < statId.getParameters().getExpressions().size(); i++)
						{
							if(!statId.getParameters().getExpressions().get(i).getType().isOfSameType(args.get(i).getType()))
							{
								throw new SemanticTypeException(statId.getLineNumber());
							}
						}
					}
				}
			}
			else
			{
				if(statId.getType() == null)
					throw new SemanticTypeException(statId.getLineNumber());
				
				if(!symbolTable.getVariable(id).getType().isOfSameType(statId.getType()))
				{
					throw new SemanticTypeException(statId.getLineNumber());
				}
			}
		} 
		else if (peekTypeMatches("IF")) 
		{
			matchTerminal("IF");
			matchNonTerminal(new Expression(scanner));
			matchTerminal("THEN");
			matchNonTerminal(new StatSequence(scanner));
			matchNonTerminal(new StatTail(scanner));
		}
		else if (peekTypeMatches("WHILE")) 
		{
			matchTerminal("WHILE");
			matchNonTerminal(new Expression(scanner));
			matchTerminal("DO");
			matchNonTerminal(new StatSequence(scanner));
			matchTerminal("ENDDO");
			matchTerminal("SEMI");
		} 
		else if (peekTypeMatches("FOR"))
		{
			matchTerminal("FOR");
			String id = scanner.peekToken().getToken();
			matchTerminal("ID");
			matchTerminal("ASSIGN");
			Expression expression1;
			matchNonTerminal(expression1 = new Expression(scanner));
			symbolTable.addVariable(new Variable(expression1.getType(), id));
			matchTerminal("TO");
			matchNonTerminal(new Expression(scanner));
			matchTerminal("DO");
			matchNonTerminal(new StatSequence(scanner));
			matchTerminal("ENDDO");
			matchTerminal("SEMI");
		} 
		else 
		{
			matchTerminal("BREAK");
			matchTerminal("SEMI");
		}
	}

	@Override
	public String getLabel() 
	{
		return "<stat>";
	}

	@Override
	public Type getType() 
	{
		return null;
	}

}
