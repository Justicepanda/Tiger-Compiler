package parser;

import scanner.TokenTuple;

public class Rule 
{
	TokenTuple[] tokens;
	
	public Rule(String rule)
	{
		String[] tokenStrings = rule.split(" ");
		tokens = new TokenTuple[tokenStrings.length];
		
		for(int i = 0; i < tokens.length; i++)
		{
			if(tokenStrings[i].equals(","))
			{
				tokens[i] = new TokenTuple("COMMA", ",");
			}
			else if(tokenStrings[i].equals("COLON"))
			{
				tokens[i] = new TokenTuple("COLON", ":");
			}
			else if(tokenStrings[i].equals(";"))
			{
				tokens[i] = new TokenTuple("SEMI", ";");
			}
			else if(tokenStrings[i].equals("("))
			{
				tokens[i] = new TokenTuple("LPAREN", "(");
			}
			else if(tokenStrings[i].equals(")"))
			{
				tokens[i] = new TokenTuple("RPAREN", ")");
			}
			else if(tokenStrings[i].equals("["))
			{
				tokens[i] = new TokenTuple("LBRACK", "[");
			}
			else if(tokenStrings[i].equals("]"))
			{
				tokens[i] = new TokenTuple("RBRACK", "]");
			}
			else if(tokenStrings[i].equals("{"))
			{
				tokens[i] = new TokenTuple("LBRACE", "{");
			}
			else if(tokenStrings[i].equals("}"))
			{
				tokens[i] = new TokenTuple("RBRACE", "}");
			}
			else if(tokenStrings[i].equals("."))
			{
				tokens[i] = new TokenTuple("PERIOD", ".");
			}
			else if(tokenStrings[i].equals("+"))
			{
				tokens[i] = new TokenTuple("PLUS", "+");
			}
			else if(tokenStrings[i].equals("-"))
			{
				tokens[i] = new TokenTuple("MINUS", "-");
			}
			else if(tokenStrings[i].equals("*"))
			{
				tokens[i] = new TokenTuple("MULT", "*");
			}
			else if(tokenStrings[i].equals("/"))
			{
				tokens[i] = new TokenTuple("DIV", "/");
			}
			else if(tokenStrings[i].equals("="))
			{
				tokens[i] = new TokenTuple("EQ", "=");
			}
			else if(tokenStrings[i].equals("!="))
			{
				tokens[i] = new TokenTuple("NEQ", "!=");
			}
			else if(tokenStrings[i].equals("<"))
			{
				tokens[i] = new TokenTuple("LESSER", "<");
			}
			else if(tokenStrings[i].equals(">"))
			{
				tokens[i] = new TokenTuple("GREATER", ">");
			}
			else if(tokenStrings[i].equals("<="))
			{
				tokens[i] = new TokenTuple("LESSEREQ", "<=");
			}
			else if(tokenStrings[i].equals(">="))
			{
				tokens[i] = new TokenTuple("GREATEREQ", ">=");
			}
			else if(tokenStrings[i].equals("&"))
			{
				tokens[i] = new TokenTuple("AND", "&");
			}
			else if(tokenStrings[i].equals("|"))
			{
				tokens[i] = new TokenTuple("OR", "|");
			}
			else if(tokenStrings[i].equals(":="))
			{
				tokens[i] = new TokenTuple("ASSIGN", ":=");
			}
			else if(tokenStrings[i].equals("array"))
			{
				tokens[i] = new TokenTuple("ARRAY", "array");
			}
			else if(tokenStrings[i].equals("break"))
			{
				tokens[i] = new TokenTuple("BREAK", "break");
			}
			else if(tokenStrings[i].equals("do"))
			{
				tokens[i] = new TokenTuple("DO", "do");
			}
			else if(tokenStrings[i].equals("else"))
			{
				tokens[i] = new TokenTuple("ELSE", "else");
			}
			else if(tokenStrings[i].equals("end"))
			{
				tokens[i] = new TokenTuple("END", "end");
			}
			else if(tokenStrings[i].equals("for"))
			{
				tokens[i] = new TokenTuple("FOR", "for");
			}
			else if(tokenStrings[i].equals("function"))
			{
				tokens[i] = new TokenTuple("FUNC", "function");
			}
			else if(tokenStrings[i].equals("if"))
			{
				tokens[i] = new TokenTuple("IF", "if");
			}
			else if(tokenStrings[i].equals("in"))
			{
				tokens[i] = new TokenTuple("IN", "in");
			}
			else if(tokenStrings[i].equals("let"))
			{
				tokens[i] = new TokenTuple("LET", "let");
			}
			else if(tokenStrings[i].equals("nil"))
			{
				tokens[i] = new TokenTuple("NIL", "nil");
			}
			else if(tokenStrings[i].equals("of"))
			{
				tokens[i] = new TokenTuple("OF", "of");
			}
			else if(tokenStrings[i].equals("then"))
			{
				tokens[i] = new TokenTuple("THEN", "then");
			}
			else if(tokenStrings[i].equals("to"))
			{
				tokens[i] = new TokenTuple("TO", "to");
			}
			else if(tokenStrings[i].equals("type"))
			{
				tokens[i] = new TokenTuple("TYPE", "type");
			}
			else if(tokenStrings[i].equals("var"))
			{
				tokens[i] = new TokenTuple("VAR", "var");
			}
			else if(tokenStrings[i].equals("while"))
			{
				tokens[i] = new TokenTuple("WHILE", "while");
			}
			else if(tokenStrings[i].equals("endif"))
			{
				tokens[i] = new TokenTuple("ENDIF", "endif");
			}
			else if(tokenStrings[i].equals("begin"))
			{
				tokens[i] = new TokenTuple("BEGIN", "begin");
			}
			else if(tokenStrings[i].equals("enddo"))
			{
				tokens[i] = new TokenTuple("ENDDO", "enddo");
			}
			else if(tokenStrings[i].equals("enddo"))
			{
				tokens[i] = new TokenTuple("ENDDO", "enddo");
			}
			else if(tokenStrings[i].contains("<"))
			{
				//This must be a non-terminal
				tokens[i] = new TokenTuple("NONTERM", tokenStrings[i]);
			}
			else if(tokenStrings[i].substring(1).equals("1") || tokenStrings[i].substring(1).equals("2") || tokenStrings[i].substring(1).equals("3")
					|| tokenStrings[i].substring(1).equals("4") || tokenStrings[i].substring(1).equals("5") || tokenStrings[i].substring(1).equals("6")
					|| tokenStrings[i].substring(1).equals("7") || tokenStrings[i].substring(1).equals("8") || tokenStrings[i].substring(1).equals("9")
					|| tokenStrings[i].substring(1).equals("0"))
			{
				tokens[i] = new TokenTuple("INTLIT", tokenStrings[i]);
			}
			else
			{
				//It's a string literal!
				tokens[i] = new TokenTuple("STRLIT", tokenStrings[i]);
			}
		}
	}
	
	public int getLength()
	{
		return tokens.length;
	}
}
