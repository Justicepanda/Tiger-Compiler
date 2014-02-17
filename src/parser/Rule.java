package parser;

import java.util.HashMap;
import java.util.Map;

public class Rule 
{
  private static Map<String, String> tokenMap = new HashMap<String, String>();
  static {
    tokenMap.put(",", "COMMA");
    tokenMap.put(":", "COLON");
    tokenMap.put(";", "SEMI");
    tokenMap.put("(", "LPAREN");
    tokenMap.put(")", "RPAREN");
    tokenMap.put("[", "LBRACK");
    tokenMap.put("]", "RBRACK");
    tokenMap.put("{", "LBRACE");
    tokenMap.put("}", "RBRACE");
    tokenMap.put(".", "PERIOD");
    tokenMap.put("+", "PLUS");
    tokenMap.put("-", "MINUS");
    tokenMap.put("*", "MULT");
    tokenMap.put("/", "DIV");
    tokenMap.put("=", "EQ");
    tokenMap.put("!=", "NEQ");
    tokenMap.put("<", "LESSER");
    tokenMap.put(">", "GREATER");
    tokenMap.put("<=", "LESSEREQ");
    tokenMap.put(">=", "GREATEREQ");
    tokenMap.put("&", "AND");
    tokenMap.put("|", "OR");
    tokenMap.put(":=", "ASSIGN");
  }
  private static String[] ruleTokenTypes;
  private static String[] tokenTypesBuilder;

  String[] tokenTypes;

  public static Rule determineFrom(String rule) {
    ruleTokenTypes = rule.split(" ");
    tokenTypesBuilder = new String[ruleTokenTypes.length];
    interpretTokens();
    return new Rule(tokenTypesBuilder);
  }

  public static void interpretTokens() {
    for(int i = 0; i < tokenTypesBuilder.length; i++)
      interpretToken(i);
  }

  private static void interpretToken(int index) {
    if (tokenMap.containsKey(ruleTokenTypes[index]))
      tokenTypesBuilder[index] = tokenMap.get(ruleTokenTypes[index]);
    else if(ruleTokenTypes[index].contains("<"))
      tokenTypesBuilder[index] = "NONTERM";
    else
      tokenTypesBuilder[index] = ruleTokenTypes[index].toUpperCase();
  }

  private Rule(String[] tokens) {
    this.tokenTypes = tokens;
  }
	
	public int getLength()
	{
		return tokenTypesBuilder.length;
	}
}
