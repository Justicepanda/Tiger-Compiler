import org.junit.Before;
import org.junit.Test;
import parser.Parser;
import scanner.Scanner;

import static org.junit.Assert.assertEquals;

public class OperationCodeGenerationTests {
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
            parser.getGeneratedCode()
    );
  }

  @Test
  public void testmultiAdd() {
    String[] arr = {"let " +
            "var a : int;" +
            "in " +
            "a := 5 + 3 + 1;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "add, t1, 3, 1\n" +
                    "add, t2, 5, t1\n" +
                    "assign, a, t2, \n",
            parser.getGeneratedCode()
    );
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
  public void testMultiSub() {
    String[] arr = {"let " +
            "var a : int;" +
            "in " +
            "a := 5 - 3 - 1;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "sub, t1, 3, 1\n" +
                    "sub, t2, 5, t1\n" +
                    "assign, a, t2, \n",
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
            parser.getGeneratedCode()
    );
  }

  @Test
  public void testMultiMult() {
    String[] arr = {"let " +
            "var a : int;" +
            "in " +
            "a := 5 * 3 * 1;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "mult, t1, 3, 1\n" +
                    "mult, t2, 5, t1\n" +
                    "assign, a, t2, \n",
            parser.getGeneratedCode()
    );
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
            parser.getGeneratedCode()
    );
  }

  @Test
  public void testMultiDiv() {
    String[] arr = {"let " +
            "var a : int;" +
            "in " +
            "a := 5 / 3 / 1;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "div, t1, 3, 1\n" +
                    "div, t2, 5, t1\n" +
                    "assign, a, t2, \n",
            parser.getGeneratedCode()
    );
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
            parser.getGeneratedCode()
    );
  }

  @Test
  public void testMultiAnd() {
    String[] arr = {"let " +
            "var a : int;" +
            "in " +
            "a := 5 & 3 & 1;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "and, t1, 3, 1\n" +
                    "and, t2, 5, t1\n" +
                    "assign, a, t2, \n",
            parser.getGeneratedCode()
    );
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
            parser.getGeneratedCode()
    );
  }

  @Test
  public void testMultiOr() {
    String[] arr = {"let " +
            "var a : int;" +
            "in " +
            "a := 5 | 3 | 1;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "or, t1, 3, 1\n" +
                    "or, t2, 5, t1\n" +
                    "assign, a, t2, \n",
            parser.getGeneratedCode()
    );
  }

  @Test
  public void multiOperation() {
    String[] arr = {"let " +
            "var a : int;" +
            "in " +
            "a := 5 * 3 - (3 + 1) & 4 | 7 / 3;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "mult, t1, 5, 3\n" +
                    "add, t2, 3, 1\n" +
                    "sub, t3, t1, t2\n" +
                    "div, t4, 7, 3\n" +
                    "or, t5, 4, t4\n" +
                    "and, t6, t3, t5\n" +
                    "assign, a, t6, \n",
            parser.getGeneratedCode()
    );
  }

  @Test
  public void lessOperation() {
    String[] arr = {"let " +
            "var a : int;" +
            "in " +
            "a := 1 < 2;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "brgeq, 1, 2, false1\n" +
                    "breq, 0, 0, true1\n" +
                    "false1:\n" +
                    "assign, t1, 0, \n" +
                    "breq, 0, 0, done1\n" +
                    "true1:\n" +
                    "assign, t1, 1, \n" +
                    "done1:\n" +
                    "assign, a, t1, \n",
            parser.getGeneratedCode()
    );
  }
}