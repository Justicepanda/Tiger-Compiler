import org.junit.Before;
import org.junit.Test;
import parser.Parser;
import scanner.Scanner;

import static org.junit.Assert.assertEquals;

public class ArrayCodeGenerationTests {
  private Scanner scanner;
  private Parser parser;

  @Before
  public void setUp() {
    scanner = new Scanner();
    parser = new Parser(scanner);
  }

  @Test
  public void simpleArrayStore() {
    String[] arr = {"let " +
            "type int_array = array[3] of int;" +
            "var a : int_array := 0;" +
            "in " +
            "a[0] := 5;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "array_store, a, 0, 0\n" +
                    "array_store, a, 1, 0\n" +
                    "array_store, a, 2, 0\n" +

                    "main:\n" +
                    "array_store, a, 0, 5\n",
            parser.getGeneratedCode());
  }

  @Test
  public void multiArrayStore() {
    String[] arr = {"let " +
            "type int_array = array[3] of int;" +
            "type int_array_array = array[2] of int_array;" +
            "var a : int_array_array := 0;" +
            "in " +
            "a[2][1] := 5;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "array_store, a, 0, 0\n" +
                    "array_store, a, 1, 0\n" +
                    "array_store, a, 2, 0\n" +
                    "array_store, a, 3, 0\n" +
                    "array_store, a, 4, 0\n" +
                    "array_store, a, 5, 0\n" +
                    "main:\n" +
                    "mult, t1, 2, 2\n" +
                    "add, t2, t1, 1\n" +
                    "array_store, a, t2, 5\n",
            parser.getGeneratedCode());
  }

  @Test
  public void multiArrayLoad() {
    String[] arr = {"let " +
            "type int_array = array[3] of int;" +
            "type int_array_array = array[2] of int_array;" +
            "var a : int_array_array := 0;" +
            "var b : int := 0;" +
            "in " +
            "b := a[1][2];" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "array_store, a, 0, 0\n" +
                    "array_store, a, 1, 0\n" +
                    "array_store, a, 2, 0\n" +
                    "array_store, a, 3, 0\n" +
                    "array_store, a, 4, 0\n" +
                    "array_store, a, 5, 0\n" +
                    "assign, b, 0, \n" +
                    "main:\n" +
                    "mult, t1, 1, 2\n" +
                    "add, t2, t1, 2\n" +
                    "array_load, t3, a, t2\n" +
                    "assign, b, t3, \n",
            parser.getGeneratedCode());
  }

  @Test
  public void arrayIf() {
    String[] arr = {"let " +
            "type int_array = array[3] of int;" +
            "var a : int_array := 0;" +
            "in " +
            "if a[1] = 2 then " +
            "endif;" +
            "end"};
    scanner.scan(arr);
    parser.parse();
    assertEquals(
            "array_store, a, 0, 0\n" +
                    "array_store, a, 1, 0\n" +
                    "array_store, a, 2, 0\n" +
                    "assign, b, 0, \n" +
                    "main:\n" +
                    "mult, t1, 1, 2\n" +
                    "add, t2, t1, 2\n" +
                    "array_load, t3, a, t2\n" +
                    "assign, b, t3, \n",
            parser.getGeneratedCode());
  }
}
