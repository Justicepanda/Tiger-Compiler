package compiler;

public class TokenTuple {
  private final String tokenType;
  private final String token;
  private String locationInfo;

  /**
   * Core concept for compilation, stores data about a scanned
   * 'token'. The type is determined by the language while the
   * token itself is the plain text scanned from the file.
   * @param tokenType The type of the token.
   * @param token The plain text of the token.
   */
  public TokenTuple(String tokenType, String token) {
    this.tokenType = tokenType;
    this.token = token;
    this.locationInfo = "";
  }

  public void setLocationInfo(String info)
  {
	  locationInfo = info;
  }
  
  public String getLocationInfo()
  {
	  return locationInfo;
  }
  
  public String getToken()
  {
	  return token;
  }
  
  public String getType() {
    return tokenType;
  }

  public String toString() {
    return "(" + tokenType + ", " + token + ")";
  }
}
