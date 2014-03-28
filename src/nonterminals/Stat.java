package nonterminals;

import parser.NoSuchIdentifierException;
import parser.ParserRule;
import symboltable.Argument;
import symboltable.Type;

import java.util.List;

public class Stat extends ParserRule {
  private StatId statId;
  private Expression expression;
  private StatSequence statSequence;
  private StatTail statTail;
  private boolean isReturnStatement;

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
    matchTerminal("BREAK");
    matchTerminal("SEMI");
  }

  private void matchFor() {
    expression = new Expression();
    matchTerminal("FOR");
    matchTerminal("ID");
    matchTerminal("ASSIGN");
    matchNonTerminal(expression);
    matchTerminal("TO");
    matchNonTerminal(new Expression());
    matchTerminal("DO");
    matchNonTerminal(new StatSequence());
    matchTerminal("ENDDO");
    matchTerminal("SEMI");
  }

  private void matchWhile() {
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
    String id = matchIdAndGetValue();
    matchNonTerminal(statId);
    matchTerminal("SEMI");

    if (getFunction(id) == null && getVariable(id) == null)
      throw new NoSuchIdentifierException(id);

    if (statId.isFunction()) {
      if (super.getFunction(id) != null) {
        if (statId.getParameters() != null) {
          List<Argument> args = getFunction(id).getArguments();
          for (int i = 0; i < statId.getParameters().size(); i++) {
            if (args != null && statId.getParameters().get(i) != null &&
                    !statId.getParameters().get(i).getType().isOfSameType(args.get(i).getType())) {
              generateException();
            }
          }
        }
      }
    } else {
      if (!getTypeOfVariable(id).isOfSameType(statId.getType())) {
        generateException();
      }
    }
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

  public Expression getExpression() {
    return expression;
  }
}
