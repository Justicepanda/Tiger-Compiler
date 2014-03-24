package nonterminals;

import parser.ParserRule;
import scanner.Scanner;
import symboltable.*;
import symboltable.Type;

public class Factor extends ParserRule 
{
  private Type type;

  public Factor(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() {
    if (peekTypeMatches("LPAREN")) {
      matchTerminal("LPAREN");
      Expression expression = new Expression(scanner);
      type = expression.getType();
      matchNonTerminal(expression);
      matchTerminal("RPAREN");
    }
    else if (peekTypeMatches("INTLIT") ||
            peekTypeMatches("STRLIT") ||
            peekTypeMatches("NIL")) {
      Constant constant = new Constant(scanner);
      type = constant.getType();
      matchNonTerminal(constant);
    }
    else if (peekTypeMatches("MINUS")) {
      matchTerminal("MINUS");
      Factor factor = new Factor(scanner);
      matchNonTerminal(factor);
      type = factor.getType();
    }
    else {
      matchTerminal("ID");
      LValue lValue = new LValue(scanner);
      type = lValue.getType();
      matchNonTerminal(lValue);
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
