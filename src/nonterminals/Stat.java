package nonterminals;

import nonterminals.statement.*;
import parser.ParserRule;
import symboltable.Type;

public class Stat extends ParserRule {
  private Statement statement;

  @Override
  public void parse() {
    storeLineNumber();
    if (peekTypeMatches("RETURN"))
      statement = new ReturnStatement();
    else if (peekTypeMatches("ID"))
      statement = new IdStatement();
    else if (peekTypeMatches("IF"))
      statement = new IfStatement();
    else if (peekTypeMatches("WHILE"))
      statement = new WhileStatement();
    else if (peekTypeMatches("FOR"))
      statement = new ForStatement();
    else
      statement = new BreakStatement();
    statement.parse();

  }

  @Override
  public String getLabel() {
    return "<stat>";
  }

  @Override
  public Type getType() {
    return null;
  }

  @Override
  public String generateCode() {
    statement.generateCode();
    return null;
  }

  public Expression getExpression() {
    return statement.getExpression();
  }

  public boolean isReturnStatement() {
    return statement instanceof ReturnStatement;
  }
}
