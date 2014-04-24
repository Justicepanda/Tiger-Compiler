package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class TigerProgram extends ParserRule {

  private final DeclarationSegment declarationSegment = new DeclarationSegment();
  private final StatSequence statSequence = new StatSequence();

  @Override
  public void parse() {
    matchTerminal("LET");
    matchNonTerminal(declarationSegment);
    matchTerminal("IN");
    matchNonTerminal(statSequence);
    matchTerminal("END");
  }

  @Override
  public String getLabel() {
    return "<tiger-program>";
  }

  @Override
  public Type getType() {
    return null;
  }

  @Override
  public String generateCode() {
	emitLabel("main:");
    declarationSegment.generateCode();
    statSequence.generateCode();
    return null;
  }
}
