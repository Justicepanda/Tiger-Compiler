package symboltable;

import java.util.ArrayList;

public class FunctionDeclaration 
{
	private final String name;
	private final ArrayList<Argument> arguments;
	private final Type returnType;
	
	public FunctionDeclaration(String name, ArrayList<Argument> args, Type returnType)
	{
		this.name = name;
		this.arguments = args;
		this.returnType = returnType;
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<Argument> getArgs()
	{
		return arguments;
	}
	
	public Type getReturnType()
	{
		return returnType;
	}
}
