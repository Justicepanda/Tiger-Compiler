package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.*;
import symboltable.Type;

public class Factor extends ParserRule {
	private Type type;

	public Factor(Scanner scanner) {
		super(scanner);
	}

	@Override
	public void parse()
	{
		lineNumber = scanner.getLineNum();
		if (peekTypeMatches("LPAREN")) 
		{
			matchTerminal("LPAREN");
			Expression expression = new Expression(scanner);
			matchNonTerminal(expression);
			type = expression.getType();
			matchTerminal("RPAREN");
		} 
		else if (peekTypeMatches("INTLIT") || peekTypeMatches("STRLIT")
				|| peekTypeMatches("NIL")) 
		{
			Constant constant = new Constant(scanner);
			matchNonTerminal(constant);
			type = constant.getType();
		} 
		else if (peekTypeMatches("MINUS")) 
		{
			matchTerminal("MINUS");
			Factor factor = new Factor(scanner);
			matchNonTerminal(factor);
			type = factor.getType();
		}
		else
		{
			String id = scanner.peekToken().getToken();
			matchTerminal("ID");
			LValue lValue = new LValue(scanner);
			matchNonTerminal(lValue);
			
			if(lValue.getType() == null)
			{
				//This is a variable, not an array
				type = symbolTable.getVariable(id).getType();
			}
			else
			{
				//This is an array
				type = lValue.getType();
			}
		}
 	}

	@Override
	public String getLabel() {
		return "<factor>";
	}

	@Override
	public Type getType() {
		return type;
	}
}
