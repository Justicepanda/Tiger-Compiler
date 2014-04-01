import org.junit.Test;
import org.junit.Before;
import parser.Parser;
import scanner.Scanner;

import static org.junit.Assert.*;

public class ControlCodeGenerationTests {
  private Scanner scanner;
  private Parser parser;

  @Before
  public void setUp() {
    scanner = new Scanner();
    parser = new Parser(scanner);
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
            "a := getchar();" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "main:\n" +
                    "callr, a, getchar\n",
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

  @Test
  public void returnStatement() {
    String[] arr = {"let " +
            "function a() : int begin " +
            "return 0;" +
            "end;" +
            "in " +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "a:\n" +
                    "return, 0, , \n" +
                    "main:\n",
            parser.getGeneratedCode());
  }

  @Test
  public void inlineVarDeclaration() {
    String[] arr = {"let " +
            "var i : int := 0;" +
            "in " +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "assign, i, 0, \n" +
                    "main:\n",
            parser.getGeneratedCode());
  }

  @Test
  public void forLoop() {
    String[] arr = {"let " +
            "var i : int := 0;" +
            "in " +
            "for i := 0 to 100 do " +
            "print(\"\");" +
            "enddo;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "assign, i, 0, \n" +
                    "main:\n" +
                    "start_loop1:\n" +
                    "brgeq, i, 100, end_loop1\n" +
                    "call, print, \"\"\n" +
                    "add, t1, i, 1\n" +
                    "assign, i, t1, \n" +
                    "goto, start_loop1, , \n" +
                    "end_loop1:\n",
            parser.getGeneratedCode());
  }

  @Test
  public void ifEqStatement() {
    String[] arr = {"let " +
            "var i : int := 0;" +
            "in " +
            "if i = 0 then " +
            "print(\"\");" +
            "endif;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "assign, i, 0, \n" +
                    "main:\n" +
                    "brneq, i, 0, after_if1\n" +
                    "call, print, \"\"\n" +
                    "after_if1:\n",
            parser.getGeneratedCode());
  }

  @Test
  public void ifLessStatement() {
    String[] arr = {"let " +
            "var i : int := 0;" +
            "in " +
            "if i < 0 then " +
            "print(\"\");" +
            "endif;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "assign, i, 0, \n" +
                    "main:\n" +
                    "brgeq, i, 0, after_if1\n" +
                    "call, print, \"\"\n" +
                    "after_if1:\n",
            parser.getGeneratedCode());
  }

  @Test
  public void whileStatement() {
	  String[] arr = {"let " +
			  "var i : int := 5;" +
			  "in " +
			  "while i > 0 "
			  + "do " +
			  "print(\"\");" +
			  "enddo;" +
			  "end" };
	  scanner.scan(arr);
	  parser.parse();
	  assertEquals("assign, i, 5, \n" +
	   "main:\n" +
	   "start_while1:\n" +
	   "brleq, i, 0, after_while1\n" +
	   "call, print, \"\"\n" +
	   "goto, start_while1, , \n" +
	   "after_while1:\n",
	   parser.getGeneratedCode());
  }

  @Test
  public void complexWhileCondition() {
    String[] arr = {"let " +
            "var i : int := 5;" +
            "in " +
            "while i + 1 > 0 "
            + "do " +
            "print(\"\");" +
            "enddo;" +
            "end" };
    scanner.scan(arr);
    parser.parse();
    assertEquals("assign, i, 5, \n" +
                    "main:\n" +
                    "start_while1:\n" +
                    "add, t1, i, 1\n" +
                    "brleq, t1, 0, after_while1\n" +
                    "call, print, \"\"\n" +
                    "goto, start_while1, , \n" +
                    "after_while1:\n",
            parser.getGeneratedCode());
  }

  @Test
  public void breakOutOfLoop() {
    String[] arr = {"let " +
            "var i : int := 5;" +
            "in " +
            "while i + 1 > 0 "
            + "do " +
            "print(\"\");" +
            "break;" +
            "enddo;" +
            "end" };
    scanner.scan(arr);
    parser.parse();
    assertEquals("assign, i, 5, \n" +
                    "main:\n" +
                    "start_while1:\n" +
                    "add, t1, i, 1\n" +
                    "brleq, t1, 0, after_while1\n" +
                    "call, print, \"\"\n" +
                    "goto, after_while1, , \n" +
                    "goto, start_while1, , \n" +
                    "after_while1:\n",
            parser.getGeneratedCode());
  }

  @Test
  public void nestedBreakOutOfLoop() {
    String[] arr = {"let " +
            "var i, j : int := 5;" +
            "in " +
            "while i + 1 > 0 " +
            "do " +
            "while j + 1 > 0 " +
            "do " +
            "print(\"\");" +
            "break;" +
            "enddo;" +
            "print(\"\");" +
            "break;" +
            "enddo;" +
            "end" };
    scanner.scan(arr);
    parser.parse();
    assertEquals("assign, i, 5, \n" +
                    "assign, j, 5, \n" +
                    "main:\n" +
                    "start_while1:\n" +
                    "add, t1, i, 1\n" +
                    "brleq, t1, 0, after_while1\n" +
                    "start_while2:\n" +
                    "add, t2, j, 1\n" +
                    "brleq, t2, 0, after_while2\n" +
                    "call, print, \"\"\n" +
                    "goto, after_while2, , \n" +
                    "goto, start_while2, , \n" +
                    "after_while2:\n" +
                    "call, print, \"\"\n" +
                    "goto, after_while1, , \n" +
                    "goto, start_while1, , \n" +
                    "after_while1:\n",
            parser.getGeneratedCode());
  }

  @Test
  public void whileLoopWithNumericExpression() {
    String[] arr = {"let " +
            "in " +
            "while 1 + 2 " +
            "do " +
            "print(\"\");" +
            "enddo;" +
            "end" };
    scanner.scan(arr);
    parser.parse();
    assertEquals("main:\n" +
                    "start_while1:\n" +
                    "add, t1, 1, 2\n" +
                    "breq, t1, 0, after_while1\n" +
                    "call, print, \"\"\n" +
                    "goto, start_while1, , \n" +
                    "after_while1:\n",
            parser.getGeneratedCode());
  }

  @Test
  public void ifStatementWithNumericExpression() {
    String[] arr = {"let " +
            "in " +
            "if 1 + 2 then " +
            "print(\"\");" +
            "endif;" +
            "end" };
    scanner.scan(arr);
    parser.parse();
    assertEquals("main:\n" +
                    "add, t1, 1, 2\n" +
                    "breq, t1, 0, after_if1\n" +
                    "call, print, \"\"\n" +
                    "after_if1:\n",
            parser.getGeneratedCode());
  }
}
