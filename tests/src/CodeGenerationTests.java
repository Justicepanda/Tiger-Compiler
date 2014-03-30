import org.junit.Test;
import org.junit.Before;
import parser.Parser;
import scanner.Scanner;

import static org.junit.Assert.*;

public class CodeGenerationTests {
  private Scanner scanner;
  private Parser parser;

  @Before
  public void setUp() {
    scanner = new Scanner();
    parser = new Parser(scanner);
  }

  @Test
  public void testSimpleAdd() {
    String[] arr = {"let " +
            "var a : int;" +
            "in " +
            "a := 5 + 3;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "add, t1, 5, 3\n" +
                    "assign, a, t1, \n",
            parser.getGeneratedCode());
  }

  @Test
  public void testSimpleSub() {
    String[] arr = {"let " +
            "var a : int;" +
            "in " +
            "a := 5 - 3;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "sub, t1, 5, 3\n" +
                    "assign, a, t1, \n",
            parser.getGeneratedCode()
    );
  }

  @Test
  public void testSimpleMult() {
    String[] arr = {"let " +
            "var a : int;" +
            "in " +
            "a := 5 * 3;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "mult, t1, 5, 3\n" +
                    "assign, a, t1, \n",
            parser.getGeneratedCode());
  }

  @Test
  public void testSimpleDiv() {
    String[] arr = {"let " +
            "var a : int;" +
            "in " +
            "a := 5 / 3;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "div, t1, 5, 3\n" +
                    "assign, a, t1, \n",
            parser.getGeneratedCode());
  }

  @Test
  public void testSimpleAnd() {
    String[] arr = {"let " +
            "var a : int;" +
            "in " +
            "a := 5 & 3;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "and, t1, 5, 3\n" +
                    "assign, a, t1, \n",
            parser.getGeneratedCode());
  }

  @Test
  public void testSimpleOr() {
    String[] arr = {"let " +
            "var a : int;" +
            "in " +
            "a := 5 | 3;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "or, t1, 5, 3\n" +
                    "assign, a, t1, \n",
            parser.getGeneratedCode());
  }

  @Test
  public void simpleFunctionCall() {
    String[] arr = {"let " +
            "in " +
            "print(\"\");" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "call, print, \"\"\n",
            parser.getGeneratedCode());
  }

  @Test
  public void functionCallWithReturn() {
    String[] arr = {"let " +
            "var a : string;" +
            "in " +
            "a := get_char();" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "callr, a, get_char\n",
            parser.getGeneratedCode());
  }

  @Test
  public void functionDeclaration() {
    String[] arr = {"let " +
            "function a() begin end;" +
            "in " +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "a:\n" +
                    "main:\n",
            parser.getGeneratedCode());
  }
}
