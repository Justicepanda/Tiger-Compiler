package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Argument;
import symboltable.Type;

import java.util.ArrayList;
import java.util.List;

public class Stat extends ParserRule {
  private StatId statId;
  private Expression expression;
  private StatSequence statSequence;
  private StatTail statTail;

  @Override
  public void parse() {
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
    String id = matchIdAndGetValue();
    matchTerminal("ASSIGN");
    matchNonTerminal(expression);
    super.addVariable(expression.getType(), id);
    matchTerminal("TO");
    //TODO semantic checking to ensure type matches here
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
    matchTerminal("THEN");
    matchNonTerminal(statSequence);
    matchNonTerminal(statTail);
  }

  private void matchReturn() {
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

    if (statId.isFunction()) {
      if (super.getFunction(id) != null) {
        //TODO remove - System.out.println("Found function! " + id + lineNumber);
        if (statId.getParameters() != null) {
          List<Argument> args = getFunction(id).getArguments();
          for (int i = 0; i < statId.getParameters().size(); i++) {
            if (args != null && statId.getParameters().get(i) != null &&
                    !statId.getParameters().get(i).getType().isOfSameType(args.get(i).getType())) {
              throw new SemanticTypeException(statId.getLineNumber());
            }
          }
        }
      }
    } else {
      if (!getTypeOfVariable(id).isOfSameType(statId.getType())) {
        throw new SemanticTypeException(statId.getLineNumber());
      }
    }
  }

  @Override
  public String getLabel() {
    return "<stat>";
  }

  @Override
  public Type getType() {
    return null;
  }

}
