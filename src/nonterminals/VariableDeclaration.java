package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import scanner.Scanner;
import symboltable.Type;
import symboltable.Variable;

public class VariableDeclaration extends ParserRule
{
	private IdList idList;
	private TypeId typeId;
	private OptionalInit optionalInit;

	public VariableDeclaration(Scanner scanner) 
	{
		super(scanner);
		idList = new IdList(scanner);
		typeId = new TypeId(scanner);
		optionalInit = new OptionalInit(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		matchTerminal("VAR");
		matchNonTerminal(idList);
		matchTerminal("COLON");
		matchNonTerminal(typeId);
		matchNonTerminal(optionalInit);
		matchTerminal("SEMI");

		if(optionalInit.getType() != null)
		{
			if(optionalInit.getType().isOfSameType(typeId.getType()))
			{
				for (String id : idList.getIds()) 
				{
					Variable var = new Variable(typeId.getType(), id);
					symbolTable.addVariable(var);
					var.setValue(optionalInit.getValue());
				}
			}
			else
			{
				throw new SemanticTypeException(typeId.getLineNumber());
			}
		}
		else
		{
			for (String id : idList.getIds()) 
			{
				Variable var = new Variable(typeId.getType(), id);
				symbolTable.addVariable(var);
			}
		}
	}

	@Override
	public String getLabel() 
	{
		return "<var-declaration>";
	}

	@Override
	public Type getType() 
	{
		return null;
	}
}
