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
            "add, 5, 3, t1\n" +
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
            "sub, 5, 3, t1\n" +
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
            "mult, 5, 3, t1\n" +
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
            "div, 5, 3, t1\n" +
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
            "and, 5, 3, t1\n" +
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
            "or, 5, 3, t1\n" +
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
            "callr, a, get_char\n",
            parser.getGeneratedCode());
  }
}
