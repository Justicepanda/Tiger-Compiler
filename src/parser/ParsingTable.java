package parser;

import dfabuilder.SvReader;
import scanner.TokenTuple;

public class ParsingTable 
{
	private int[][] table;
	private SvReader fileReader;
	private String[] terminals; //Starts at index 1
	private String[] nonterminals; //Starts at index 0
	
	public ParsingTable(String fileName)
	{
		fileReader = new SvReader(',');
		fileReader.read(fileName);
		int currentLine = 0;
		
		while(fileReader.hasLine())
		{
			terminals = fileReader.getHeader();
			String[] cells = fileReader.getLine();
			for(int i = 0; i < cells.length - 1; i++)
			{
				try
				{
					nonterminals[currentLine] = cells[0];
					table[currentLine][i] = Integer.parseInt(cells[i + 1]);
				}
				catch(Exception e)
				{
					System.out.println("Incorrect input into parsing table");
				}
			}
			currentLine++;
		}
	}
	
	public int getTerminalIndex(TokenTuple terminal)
	{
		for(int i = 0; i < terminals.length; i++)
		{
			if(terminals[i].equals(terminal))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public int getNonTerminalIndex(TokenTuple nonterminal)
	{
		for(int i = 0; i < terminals.length; i++)
		{
			if(nonterminals[i].equals(nonterminal))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public int getCell(TokenTuple terminal, TokenTuple nonterminal)
	{
		int term = getTerminalIndex(terminal);
		int nonterm = getNonTerminalIndex(nonterminal);
		
		if(term != -1 && nonterm != -1)
			return table[term][nonterm];
		else
			return -1;
	}
	
	public int getWidth()
	{
		return terminals.length - 1;
	}
	
	public int getHeight()
	{
		return nonterminals.length;
	}
}
