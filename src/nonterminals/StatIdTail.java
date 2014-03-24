package nonterminals;

import parser.ParserRule;
import parser.SemanticTypeException;
import symboltable.Argument;
import symboltable.Type;

import java.util.ArrayList;
import java.util.List;

public class StatIdTail extends ParserRule {
  private Type type;
  private String id;
  private StatIdTailTail statIdTailTail;
  private Expression expression;

  @Override
  public void parse() {
    storeLineNumber();
    if (peekTypeMatches("ID")) {
      statIdTailTail = new StatIdTailTail();
      id = matchIdAndGetValue();
      matchNonTerminal(statIdTailTail);
      semanticCheck();
    } else {
      expression = new Expression();
      matchNonTerminal(expression);
      type = expression.getType();
    }
  }

  private void semanticCheck() {
    if (statIdTailTail.getType() == null && statIdTailTail.getParameters() != null) {
      if (getFunction(id) != null) {
        type = getFunction(id).getReturnType();
        if (type != null && !type.isOfSameType(statIdTailTail.getType()))
          generateException();
        List<Argument> args = getFunction(id).getArguments();
        for (int i = 0; i < statIdTailTail.getParameters().size(); i++) {
          if (args != null && !statIdTailTail.getParameters().get(i).getType().isOfSameType(args.get(i).getType()))
            generateException();
        }
      } else {
        if (super.getVariable(id) != null)
          type = getVariable(id).getType();
      }
    } else {
      type = statIdTailTail.getType();
    }
  }

  @Override
  public String getLabel() {
    return "<stat-id-tail>";
  }

  @Override
  public Type getType() {
    return type;
  }

}
