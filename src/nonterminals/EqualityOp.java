package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class EqualityOp extends ParserRule {
  private String inverseOp;

  @Override
  public void parse() {
    if (peekTypeMatches("NEQ")) {
      matchTerminal("NEQ");
      inverseOp = "breq";
    }
    else if (peekTypeMatches("EQ")) {
      matchTerminal("EQ");
      inverseOp = "brneq";
    }
    else if (peekTypeMatches("LESSER")) {
      matchTerminal("LESSER");
      inverseOp = "brgeq";
    }
    else if (peekTypeMatches("GREATER")) {
      matchTerminal("GREATER");
      inverseOp = "brleq";
    }
    else if (peekTypeMatches("LESSEREQ")) {
      matchTerminal("LESSEREQ");
      inverseOp = "brgt";
    }
    else {
      matchTerminal("GREATEREQ");
      inverseOp = "brlt";
    }
  }

  @Override
  public String getLabel() {
    return "<ineq>";
  }

  @Override
  public Type getType() {
    return null;
  }

  @Override
  protected String generateCode() {
    return null;
  }

  public String getInverseOp() {
    return inverseOp;
  }
}
