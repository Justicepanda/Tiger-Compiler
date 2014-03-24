package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import scanner.Scanner;
import symboltable.*;
import symboltable.Type;

public class AddTerm extends ParserRule {
  private int lineNum;
  private Type multType;
  private Type addType;

	public AddTerm(Scanner scanner)
	{
		super(scanner);
	}
	
	@Override
	public void parse() 
	{
    lineNum = scanner.getLineNum();
    MultTerm multTerm = new MultTerm(scanner);
    matchNonTerminal(multTerm);
    multType = multTerm.getType();
    AddTerm2 addTerm2 = new AddTerm2(scanner);
    matchNonTerminal(addTerm2);
    addType = addTerm2.getType();
	}

  @Override
  public String getLabel() {
    return "<add-term>";
  }

  @Override
  public Type getType() {
    if (multType.isOfSameType(addType))
      return multType;
    throw new SemanticTypeException(lineNum);
  }

}
