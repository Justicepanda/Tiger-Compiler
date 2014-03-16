package parser;

import java.util.ArrayList;

public class AttributeRule 
{	
	private String[] leftTokens;
	private String[] rightTokens;
	
	public AttributeRule(String[] leftTokens, String[] rightTokens)
	{
		this.leftTokens = leftTokens;
		this.rightTokens = rightTokens;
	}
	
	public static AttributeRule determineFrom(String rule)
	{
		ArrayList<String> leftTokens = new ArrayList<String>();
		ArrayList<String> rightTokens = new ArrayList<String>();
		
		String[] leftandright = rule.split("<-");
		String[] left = leftandright[0].split(" ");
		String[] right = leftandright[1].split(" ");
		
		for(int i = 0; i < left.length; i++)
		{
			leftTokens.add(left[i]);
		}
		
		for(int j = 0; j < right.length; j++)
		{
			rightTokens.add(right[j]);
		}
		
		return new AttributeRule((String[])leftTokens.toArray(), (String[])rightTokens.toArray());
	}
}
