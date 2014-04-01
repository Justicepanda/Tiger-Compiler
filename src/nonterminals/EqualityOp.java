package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class EqualityOp extends ParserRule {
  private String inverseOp;
  private String op = "";

  @Override
  public void parse() {
    if (peekTypeMatches("NEQ")) {
      matchTerminal("NEQ");
      inverseOp = "breq";
      op = "neq";
    }
    else if (peekTypeMatches("EQ")) {
      matchTerminal("EQ");
      inverseOp = "brneq";
      op = "eq";
    }
    else if (peekTypeMatches("LESSER")) {
      matchTerminal("LESSER");
      inverseOp = "brgeq";
      op = "less";
    }
    else if (peekTypeMatches("GREATER")) {
      matchTerminal("GREATER");
      inverseOp = "brleq";
      op = "greater";
    }
    else if (peekTypeMatches("LESSEREQ")) {
      matchTerminal("LESSEREQ");
      inverseOp = "brgt";
      op = "leq";
    }
    else {
      matchTerminal("GREATEREQ");
      inverseOp = "brlt";
      op = "geq";
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
  public String generateCode() {
    return null;
  }

  public String getInverseOp() {
    return inverseOp;
  }

  public String getOp() {
    return op;
  }
}
