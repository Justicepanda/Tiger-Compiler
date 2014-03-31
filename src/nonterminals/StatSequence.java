package nonterminals;

import parser.ParserRule;
import symboltable.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatSequence extends ParserRule {
  private Stat stat;
  private StatSequence statSequence;

  @Override
  public void parse() {
    if (isStatement())
      matchStatement();
  }

  private void matchStatement() {
    stat = new Stat();
    statSequence = new StatSequence();
    matchNonTerminal(stat);
    matchNonTerminal(statSequence);
  }

  private boolean isStatement() {
    return peekTypeMatches("RETURN") || peekTypeMatches("ID")
            || peekTypeMatches("IF") || peekTypeMatches("WHILE")
            || peekTypeMatches("FOR") || peekTypeMatches("BREAK");
  }

  @Override
  public String getLabel() {
    return "<stat-seq>";
  }

  @Override
  public Type getType() {
    return null;
  }

  @Override
  public String generateCode() {
    if (stat == null)
      return null;
    stat.generateCode();
    statSequence.generateCode();
    return null;

  }

  public List<Expression> getReturnExpressions() {
    List<Stat> stats = getStatements();
    List<Expression> returnExprs = new ArrayList<Expression>();
    for (Stat stat: stats)
      if (stat.isReturnStatement())
        returnExprs.add(stat.getExpression());
    return returnExprs;
  }

  List<Stat> getStatements() {
    if (statSequence == null)
      return new ArrayList<Stat>();
    List<Stat> listSoFar = statSequence.getStatements();
    if (listSoFar == null)
      listSoFar = new ArrayList<Stat>();
    listSoFar.add(stat);
    Collections.reverse(listSoFar);
    return listSoFar;
  }
}
