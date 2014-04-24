package backend;

public class LiveRange 
{
	private int startingLine;
	private int endingLine;
	
	public LiveRange(int start)
	{
		startingLine = start;
	}
	
	public void setStart(int start)
	{
		startingLine = start;
	}
	
	public void setEnd(int end)
	{
		endingLine = end;
	}
	
	public int getStart()
	{
		return startingLine;
	}
	
	public int getEnd()
	{
		return endingLine;
	}
}
