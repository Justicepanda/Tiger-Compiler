package compiler;

public class TokenTuple {
  private final String tokenType;
  private final String token;

  public TokenTuple(String tokenType, String token) {
    this.tokenType = tokenType;
    this.token = token;
  }

  public String getToken()
  {
	  return token;
  }
  
  public String getType() {
    return tokenType;
  }
}
