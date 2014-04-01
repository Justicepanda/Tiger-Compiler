import org.junit.Before;
import org.junit.Test;
import parser.*;
import scanner.Scanner;
import utilities.NormalFileScraper;

public class TigerParserTests {
  private Parser parser;
  private Scanner scanner;

  @Before
  public void setUp() {
    scanner = new Scanner();
    parser = new Parser(scanner);
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

  @Test(expected = SemanticTypeException.class)
  public void mismatchedTypeOnDeclaration() {
    String[] arr = {"let var a : int := \"\"; in end"};
    scanner.scan(arr);
    parser.parse();
  }

  @Test(expected = SemanticTypeException.class)
  public void mismatchedTypeInline() {
    String[] arr = {"let var a : int; var b : string; in a := a + b; end"};
    scanner.scan(arr);
    parser.parse();
  }

  @Test(expected = SemanticTypeException.class)
  public void mismatchedTypeInFunctionCall() {
    String[] arr = {"let " +
            "var a : int; " +
            "var b : string; " +
            "function c (d:string, e:int) begin " +
            "end; " +
            "in c(a,b); end"};
    scanner.scan(arr);
    parser.parse();
  }

  @Test(expected = SemanticTypeException.class)
  public void mismatchedTypeInFunctionAssignment() {
    String[] arr = {
            "let " +
                    "var a : int;" +
                    "function b() : string begin " +
                    "return \"\";" +
                    "end;" +
                    "in a := b();" +
                    "end"};
    scanner.scan(arr);
    parser.parse();
  }

  @Test(expected = SemanticTypeException.class)
  public void mismatchedReturnTypeInFunction() {
    String[] arr = {
            "let " +
                    "function a() : string begin " +
                    "return 0;" +
                    "end;" +
                    "in end"};
    scanner.scan(arr);
    parser.parse();
  }

  @Test
  public void arrayTypeShouldBeAddedToTable() {
    String[] arr = {
            "let " +
                    "type int_array = array [10] of int;" +
                    "in end"};
    scanner.scan(arr);
    parser.parse();
  }

  @Test
  public void aliasShouldBeAssignableToConst() {
    String[] arr = {
            "let " +
                    "type int_alias = int;" +
                    "var a : int_alias := 5;" +
                    "in end"
    };
    scanner.scan(arr);
    parser.parse();
  }

  @Test(expected = SemanticTypeException.class)
  public void aliasShouldNotBeAssignableToOther() {
    String[] arr = {
            "let " +
                    "type int_alias = int;" +
                    "var a : int := 5;" +
                    "var b : int_alias;" +
                    "in " +
                    "b := a;" +
                    "end"
    };
    scanner.scan(arr);
    parser.parse();
  }

  @Test
  public void arrayShouldBeAddedToTable() {
    String[] arr = {
            "let " +
                    "type int_array = array [10] of int;" +
                    "var a : int_array;" +
                    "in end"};
    scanner.scan(arr);
    parser.parse();
  }

  @Test
  public void arrayShouldBeAssignableToConst() {
    String[] arr = {
            "let " +
                    "type int_arr = array [10] of int;" +
                    "var a : int_arr := 5;" +
                    "in end"
    };
    scanner.scan(arr);
    parser.parse();
  }

  @Test(expected = SemanticTypeException.class)
  public void ifMustContainInt() {
    String[] arr = {
            "let " +
                    "in " +
                    "if \"hello\" then endif;" +
                    "end"
    };
    scanner.scan(arr);
    parser.parse();
  }

  @Test(expected = SemanticTypeException.class)
  public void whileMustContainInt() {
    String[] arr = {
            "let " +
                    "in " +
                    "while \"hello\" do enddo;" +
                    "end"
    };
    scanner.scan(arr);
    parser.parse();
  }

  @Test
  public void whileRunsWithInt() {
    String[] arr = {
            "let " +
                    "in " +
                    "while 5 do enddo;" +
                    "end"
    };
    scanner.scan(arr);
    parser.parse();
  }

  @Test(expected = NoSuchIdentifierException.class)
  public void callingNonexistentVariableGeneratesException() {
    String[] arr = {
            "let " +
                    "in " +
                    "while a do enddo;" +
                    "end"
    };
    scanner.scan(arr);
    parser.parse();
  }

  @Test(expected = NoSuchTypeException.class)
  public void callingNonexistentTypeGeneratesException() {
    String[] arr = {
            "let " +
                    "var a : no_type;" +
                    "in " +
                    "end"
    };
    scanner.scan(arr);
    parser.parse();
  }

  @Test(expected = NoSuchIdentifierException.class)
  public void callingNonexistentFunctionGeneratesException() {
    String[] arr = {
            "let " +
                    "in " +
                    "no_func();" +
                    "end"
    };
    scanner.scan(arr);
    parser.parse();
  }

  @Test(expected = SemanticTypeException.class)
  public void functionWithNoReturnValueCannotHaveReturnStatement() {
    String[] arr = {
            "let " +
                    "function a() begin return 1; end;" +
                    "in end "};
    scanner.scan(arr);
    parser.parse();
  }

  @Test(expected = SemanticTypeException.class)
  public void forLoopWithBadConstants() {
    String[] arr = {
            "let " +
                    "var i : int := 0;" +
                    "in " +
                    "for i := 5+3*4/2-1=10&1 to 0 do " +
                    "enddo; " +
                    "end"};
    scanner.scan(arr);
    parser.parse();
  }

  @Test
  public void negativeFactor() {
    String[] arr = {
            "let " +
                    "var i : int := 0;" +
                    "in " +
                    "i := 5 + -5;" +
                    "end"};
    scanner.scan(arr);
    parser.parse();
  }
}
