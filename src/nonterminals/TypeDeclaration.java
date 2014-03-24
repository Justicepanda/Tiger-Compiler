package nonterminals;

import parser.ParserRule;
import scanner.Scanner;

class TypeDeclaration extends ParserRule 
{
	private Type type;

	TypeDeclaration(Scanner scanner) 
	{
		super(scanner);
	}

	@Override
	public void parse() 
	{
		lineNumber = scanner.getLineNum();
		matchTerminal("TYPE");
		String id = peekTokenValue();
		matchTerminal("ID");
		matchTerminal("EQ");
		matchNonTerminal(type = new Type(scanner));
		matchTerminal("SEMI");

		symbolTable.addType(new symboltable.Type(id, type.getType().getActualType()));
	}

	@Override
	public String getLabel() 
	{
		return "<type-declaration>";
	}

	@Override
	public symboltable.Type getType() 
	{
		return null;
	}
}
