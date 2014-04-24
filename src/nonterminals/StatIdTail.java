package nonterminals;

import parser.NoSuchIdentifierException;
import parser.ParserRule;
import symboltable.Argument;
import symboltable.Type;

import java.util.List;

public class StatIdTail extends ParserRule {
  private Type type;
  private String id;
  private StatIdTailTail statIdTailTail;
  private Expression expression;

  @Override
  public void parse() {
    storeLineNumber();
    if (peekTypeMatches("ID"))
      matchTail();
    else
      matchExpression();
  }

  private void matchTail() {
    statIdTailTail = new StatIdTailTail();
    id = matchIdAndGetValue();
    matchNonTerminal(statIdTailTail);
    semanticCheck();
  }

  private void matchExpression() {
    expression = new Expression();
    matchNonTerminal(expression);
    type = expression.getType();
  }

  private void semanticCheck() {
    if (noSuchIdentifier())
      throw new NoSuchIdentifierException(id);
    if (ifTailHasNoType())
      acquireAndCheckType();
    else
      type = statIdTailTail.getType();
  }

  private boolean noSuchIdentifier() {
    return getFunction(id) == null && getVariable(id) == null;
  }

  private boolean ifTailHasNoType() {
    return statIdTailTail.getType().isOfSameType(Type.NIL_TYPE);
  }

  private void acquireAndCheckType() {
    if (isFunction()) {
      checkTypeOfFunction();
      checkTypesOfArguments();
    } else if (isVariable()) {
      checkTypeOfVariable();
    }
  }

  private boolean isFunction() {
    return getFunction(id) != null;
  }

  public boolean isReturnedFunction() {
    return statIdTailTail != null && statIdTailTail.isFunction();
  }

  private void checkTypeOfFunction() {
    type = getFunction(id).getReturnType();
    if (typeExistsAndMismatches())
      generateException();
  }

  private void checkTypeOfVariable() {
    type = getVariable(id).getType();
    if (!type.isOfSameType(statIdTailTail.getType()))
      generateException();
  }

  private boolean isVariable() {
    return getVariable(id) != null;
  }

  private boolean typeExistsAndMismatches() {
    return type != null && !type.isOfSameType(statIdTailTail.getType());
  }

  private void checkTypesOfArguments() {
    List<Argument> args = getFunction(id).getArguments();
    for (int i = 0; i < statIdTailTail.getParameters().size(); i++)
      if (argumentExistsAndMisMatches(args, i))
        generateException();
  }

  private boolean argumentExistsAndMisMatches(List<Argument> args, int i) {
    return args != null && !statIdTailTail.getParameters().get(i).getType().isOfSameType(args.get(i).getType());
  }

  @Override
  public String getLabel() {
    return "<stat-id-tail>";
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public String generateCode() {
    if (expression != null)
      return expression.generateCode();
    else {
      if (!statIdTailTail.isFunction()) {
        statIdTailTail.setId(id);
        String temp = statIdTailTail.generateCode();
        String newTemp = newTemp();
        if (statIdTailTail.isArray())
        {
          emit("array_load, " + newTemp + ", " + id + ", " + temp);
          return newTemp;
        }
        else
        	return temp;
      }
      return null;
    }
  }

  public List<Expression> getParameters() {
    return statIdTailTail.getParameters();
  }

  public String getFunctionId() {
    return id;
  }
}
