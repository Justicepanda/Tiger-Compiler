package nonterminals.statement;

import nonterminals.Expression;
import nonterminals.StatId;
import parser.NoSuchIdentifierException;
import symboltable.Argument;
import symboltable.Array;

import java.util.List;

public class IdStatement extends Statement {
  private StatId statId;
  private String id;

  @Override
  public Expression getExpression() {
    return null;
  }

  @Override
  public void parse() {
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
    for (int i = 0; i < statId.getParameters().size(); i++) {
      if (argDoesNotMatchType(args, i))
        generateException();
    }
  }

  private boolean argDoesNotMatchType(List<Argument> args, int i) {
    return args != null && statId.getParameters().get(i) != null &&
            !statId.getParameters().get(i).getType().isOfSameType(args.get(i).getType());
  }

  @Override
  public String generateCode() {
    String statIdId = statId.generateCode();
    if (statId.isFunction())
      emit("call, " + id + printParameters());
    else if (statId.isReturnedFunction())
      emit("callr, " + id + ", " + statId.getFunctionId() + printParameters());
    else {
      if (getVariable(id) instanceof Array) {
        statId.setDimensions(((Array)getVariable(id)).getDimensions());
        emit("array_store, " + id + ", " + statId.getLeftArrayIndex() + ", " + statIdId);
      }
      else {
        emit("assign, " + id + ", " + statIdId + ", ");
      }
    }
    return null;
  }

  private String printParameters() {
    if (statId.hasParameters())
      return ", " + statId.printParameters();
    else
      return "";
  }
}
