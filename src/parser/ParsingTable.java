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
		terminals = new String[49];
		nonterminals = new String[47];
		table = new int[47][49];
		
		fileReader = new SvReader(',');
		fileReader.read(fileName);
		int currentLine = 0;
		
		while(fileReader.hasLine())
		{
			String[] tempterminals = fileReader.getHeader();
			
			//Quick hack.. couldn't figure out why it was adding quotation marks into the mix
			terminals = new String[49];
			for(int i = 0; i < 49; i++)
			{
				terminals[i] = tempterminals[i + 2];
			}
			terminals[0] = ",";
			
			String[] cells = fileReader.getLine();
			for(int i = 0; i < 49; i++)
			{
				try
				{
					nonterminals[currentLine] = cells[0];
					table[currentLine][i] = Integer.parseInt(cells[i + 1]);
				}
				catch(Exception ex)
				{
					//It was an empty cell, don't do anything
				}
			}
			currentLine++;
		}
	}
	
	public int getTerminalIndex(TokenTuple terminal)
	{
		for(int i = 0; i < terminals.length; i++)
		{
			if(terminals[i].equals(terminal.getToken()) || terminals[i].toLowerCase().equals(terminal.toString().toLowerCase()))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public int getNonTerminalIndex(TokenTuple nonterminal)
	{
		for(int i = 0; i < nonterminals.length; i++)
		{
			if(nonterminals[i].equals(nonterminal.getToken()))
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
			return table[nonterm][term];
		else
			return -1;
	}
	
	public int getWidth()
	{
		return terminals.length - 1;
	}
	
	public int getHeight()
	{
		return nonterminals.length - 1;
	}
}
