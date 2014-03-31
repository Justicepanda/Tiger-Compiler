package nonterminals;

import parser.NoSuchIdentifierException;
import parser.ParserRule;
import symboltable.Argument;
import symboltable.Type;

import java.util.List;
import java.util.Stack;

public class Stat extends ParserRule {
  private StatId statId;
  private Expression expression;
  private StatSequence statSequence;
  private StatTail statTail;
  private boolean isReturnStatement;
  private boolean isForStatement;
  private boolean isWhileStatement;
  private String id;
  private Expression expression2;
  private boolean isIfStatement;
  private boolean isBreakStatement;
  private Stack<String> endLabelStack;
  
  @Override
  public void parse() {
    storeLineNumber();
    if (peekTypeMatches("RETURN"))
      matchReturn();
    else if (peekTypeMatches("ID"))
      matchLeadingId();
    else if (peekTypeMatches("IF"))
      matchIf();
    else if (peekTypeMatches("WHILE"))
      matchWhile();
    else if (peekTypeMatches("FOR"))
      matchFor();
    else
      matchBreak();
  }

  private void matchBreak() {
	isBreakStatement = true;
    matchTerminal("BREAK");
    matchTerminal("SEMI");
  }

  private void matchFor() {
    isForStatement = true;
    expression = new Expression();
    expression2 = new Expression();
    statSequence = new StatSequence();
    matchTerminal("FOR");
    id = matchIdAndGetValue();
    matchTerminal("ASSIGN");
    matchNonTerminal(expression);
    matchTerminal("TO");
    matchNonTerminal(expression2);
    matchTerminal("DO");
    matchNonTerminal(statSequence);
    matchTerminal("ENDDO");
    matchTerminal("SEMI");
  }

  private void matchWhile() {
	  isWhileStatement = true;
    expression = new Expression();
    statSequence = new StatSequence();
    matchTerminal("WHILE");
    matchNonTerminal(expression);
    if (!(expression.getType().isExactlyOfType(Type.INT_TYPE)))
      generateException();
    matchTerminal("DO");
    matchNonTerminal(statSequence);
    matchTerminal("ENDDO");
    matchTerminal("SEMI");
  }

  private void matchIf() {
    isIfStatement = true;
    expression = new Expression();
    statSequence = new StatSequence();
    statTail = new StatTail();
    matchTerminal("IF");
    matchNonTerminal(expression);
    if (!(expression.getType().isExactlyOfType(Type.INT_TYPE)))
      generateException();
    matchTerminal("THEN");
    matchNonTerminal(statSequence);
    matchNonTerminal(statTail);
  }

  private void matchReturn() {
    isReturnStatement = true;
    expression = new Expression();
    matchTerminal("RETURN");
    matchNonTerminal(expression);
    matchTerminal("SEMI");
  }

  private void matchLeadingId() {
    statId = new StatId();
    id = matchIdAndGetValue();
    matchNonTerminal(statId);
    matchTerminal("SEMI");
    semanticCheck(id);
  }

  private void semanticCheck(String id) {
    if (getFunction(id) == null && getVariable(id) == null)
      throw new NoSuchIdentifierException(id);
    if (statId.isFunction())
      checkArgumentsIfExist(id);
    else if (!getTypeOfVariable(id).isOfSameType(statId.getType()))
      generateException();
  }

  private void checkArgumentsIfExist(String id) {
    if (super.getFunction(id) != null && statId.getParameters() != null)
      checkArguments(id);
  }

  private void checkArguments(String id) {
    List<Argument> args = getFunction(id).getArguments();
    for (int i = 0; i < statId.getParameters().size(); i++)
      if (argDoesNotMatchType(args, i))
        generateException();
  }

  private boolean argDoesNotMatchType(List<Argument> args, int i) {
    return args != null && statId.getParameters().get(i) != null &&
            !statId.getParameters().get(i).getType().isOfSameType(args.get(i).getType());
  }

  public boolean isReturnStatement() {
    return isReturnStatement;
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
  protected String generateCode() {
    if (statId != null) {
      String statIdId = statId.generateCode();
      if (statId.isFunction())
        emit("call, " + id + printParameters());
      else if (statId.isReturnedFunction())
        emit("callr, " + id + ", " + statId.getFunctionId() + printParameters());
      else
        emit("assign, " + id + ", " + statIdId + ", ");
    }
    else if (isReturnStatement) {
      emit("return, " + expression.generateCode() + ", , ");
    }
    else if (isForStatement) {
      String startLabel = newLabel("start_loop");
      String endLabel = newLabel("end_loop");
      endLabelStack.add(endLabel);
      emit(startLabel + ":");
      emit("brgeq, " + id + ", " + expression2.generateCode() + ", " + endLabel);
      statSequence.generateCode();
      String temp = newTemp();
      emit("add, " + temp + ", " + id + ", 1");
      emit("assign, " + id + ", " + temp + ", ");
      emit("goto, " + startLabel + ", , ");
      emit(endLabel + ":");
    }
    else if (isIfStatement) {
      String endLabel = newLabel("after_if");
      endLabelStack.add(endLabel);
      if (expression.hasEqualityOperation())
        emit(expression.getCodeEqualityOperation() + ", " + endLabel);
      statSequence.generateCode();
      emit(endLabel + ":");

    }
    else if(isWhileStatement) {
      String startWhile = newLabel("start_while");
      String afterWhile = newLabel("after_while");
      endLabelStack.add(afterWhile);
      emit(startWhile + ":");
      if (expression.hasEqualityOperation()) {
        emit(expression.getCodeEqualityOperation() + ", " + afterWhile);
      }
    	statSequence.generateCode();
    	emit("goto, " + startWhile + ", , ");
    	emit(afterWhile + ":");
    }
    else if(isBreakStatement) {
    	emit("goto, " + endLabelStack.pop() + ", , ");
    }
    return null;
  }

  private String printParameters() {
    if (statId.hasParameters())
      return ", " + statId.printParameters();
    else
      return "";
  }

  public Expression getExpression() {
    return expression;
  }
}
