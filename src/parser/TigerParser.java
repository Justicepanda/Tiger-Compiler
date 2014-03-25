package parser;

import nonterminals.TigerProgram;
import scanner.Scanner;

public class TigerParser 
{
  private boolean debug;
	private final Scanner scanner;
  private TigerProgram program;

  public TigerParser(Scanner scanner)
	{
    program = new TigerProgram();
    this.scanner = scanner;
	}

	public void parse()
	{
    ParserRule.setScanner(scanner);
		ParserRule.reset();
		program.parse();
    if (debug)
      print();
	}
	
	public String print() {
		return ParserRule.print();
	}

  public void setDebug() {
    this.debug = true;
    program.setDebug();
  }
}