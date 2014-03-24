package scanner;

class LinesHandler
{
	private int lineInd;
	private int charInd;
	private final String[] lines;

	LinesHandler(String[] lines) 
	{
		for (int i = 0; i < lines.length; i++)
			lines[i] += " ";
		this.lines = lines;
		reset();
	}

	private void reset()
	{
		lineInd = 0;
		charInd = 0;
	}

	char getCurrentChar() 
	{
		if (lineInd < lines.length)
			return lines[lineInd].charAt(charInd);
		return ' ';
	}

	boolean hasChars() 
	{
		return lineInd < lines.length;
	}

	boolean isAtSpaceChar() 
	{
		char c = getCurrentChar();
		return c == ' ' || c == '\t' || c == '\n';
	}

	void moveForward() 
	{
		charInd++;
		if (noCharsLeftInLine())
			moveToNextLine();
	}

	void moveBackward() 
	{
		charInd--;
		if (charInd < 0)
			moveToPrevLine();
	}

	void printLexicalException()
	{
		moveBackward();
		System.out.println("Lexical error (line: " + (lineInd + 1) + "): \""
				+ getCurrentChar() + "\" does not begin a valid token.");
		moveForward();
	}

	private void moveToPrevLine() 
	{
		lineInd--;
		if (lineInd < 0)
			reset();
		else
			charInd = lines[lineInd].length() - 1;
	}

	private void moveToNextLine() 
	{
		charInd = 0;
		lineInd++;
	}

	private boolean noCharsLeftInLine()
	{
		return charInd >= lines[lineInd].length();
	}

	String getLineUpToCurrChar() 
	{
		if (lineInd < lines.length)
			return lines[lineInd].substring(0, charInd);
		else
			return "";
	}

	String getLineInfo() 
	{
		int movesBackward = 0;
		while (getCurrentChar() == ' ') 
		{
			movesBackward++;
			moveBackward();
		}
		String line = "(line " + (lineInd + 1) + "): " + getLineUpToCurrChar();
		while (movesBackward > 0) 
		{
			movesBackward--;
			moveForward();
		}
		return line;
	}

	public int getCurrentLine() 
	{
		return lineInd;
	}
}
