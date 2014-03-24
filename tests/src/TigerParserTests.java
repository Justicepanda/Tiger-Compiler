import org.junit.Before;
import org.junit.Test;
import parser.TigerParser;
import scanner.Scanner;
import scanner.TokenDfa;
import scanner.TokenDfaBuilder;
import utilities.NormalFileScraper;

public class TigerParserTests {
  private TigerParser parser;
  private Scanner scanner;

  @Before
  public void setUp() {
    scanner = new Scanner((TokenDfa) new TokenDfaBuilder().buildFrom("TokenDFA.csv"));
    parser = new TigerParser(scanner);
  }

  @Test
  public void simplestCase() {
    String[] arr = {"let in end"};
    scanner.scan(arr);
    parser.parse();
  }

  @Test
  public void withTypeDeclaration() {
    String[] arr = {"let type blah = int; in end"};
    scanner.scan(arr);
    parser.parse();
  }

  @Test
  public void withVariableDeclarations() {
    String[] arr = {"let var a,b,c,d,e,f,g : int := 5; in end"};
    scanner.scan(arr);
    parser.parse();
  }

  @Test
  public void withFunctionDeclaration() {
    String[] arr = {"let function a (b:int, c:int, d:string, e:int) : int begin end; in end"};
    scanner.scan(arr);
    parser.parse();
  }

  @Test
  public void bigFile() {
    String[] arr = new NormalFileScraper().read("./tests/ex8.tiger");
    scanner.scan(arr);
    parser.parse();
  }
}
