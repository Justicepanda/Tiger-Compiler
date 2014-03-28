package compiler;

import parser.Parser;
import utilities.NormalFileScraper;
import scanner.Scanner;

public class Compiler 
{
	private final Scanner scanner;
	private final Parser parser;
	private static boolean errorEncountered;

	Compiler(Scanner scanner, Parser parser) {
		this.scanner = scanner;
		this.parser = parser;
	}

	/**
	 * Attempts to compile the file found at filename. The compiler will reject
	 * any files that have lexical or parsing errors. Once compilation is
	 * complete, an output message is available through a call to getMessage(),
	 * and the generated parsing tree is available through a call to
	 * getParseTreePrintout().
	 * 
	 * @param filename
	 *            The file to be compiled.
	 */
	public void compile(String filename) 
	{
		scanner.reset();
		scanner.scan(getLinesFromFile(filename));
		parser.parse();
		if(!errorEncountered)
			System.out.println("\nCompilation Successful!");
		else
			System.out.println("\nCompilation Unsuccessful.");
	}

	private String[] getLinesFromFile(String filename) 
	{
		NormalFileScraper scraper = new NormalFileScraper();
		return scraper.read(filename);
	}
	
	public static void errorOccured()
	{
		errorEncountered = true;
	}
}
