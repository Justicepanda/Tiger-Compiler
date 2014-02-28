import compiler.Compiler;
import org.junit.Before;
import org.junit.Test;
import parser.Parser;
import scanner.Scanner;
import scanner.TokenDfa;
import scanner.TokenDfaBuilder;

import java.io.*;

import static junit.framework.Assert.*;

public class Phase1Tests {

  private Compiler compiler;

  @Before
  public void setUp() {
    Scanner scanner = new Scanner((TokenDfa) new TokenDfaBuilder().buildFrom("TokenDFA.csv"));
    Parser parser = new Parser("ParsingTable.csv", "GrammarRules");
    compiler = Compiler.debug(scanner, parser);
  }

  private void compareFileToMessage(String filename, String message) {
    String exp = readFile(filename);
    assertEquals(exp, message);
  }

  @Test
  public void ex1() {
    String message = compiler.compile("./tests/ex1.tiger");
    compareFileToMessage("./tests/ex1.out", message);
  }

  @Test
  public void ex2() {
    String message = compiler.compile("./tests/ex2.tiger");
    compareFileToMessage("./tests/ex2.out", message);
  }

  @Test
  public void ex3() {
    String message = compiler.compile("./tests/ex3.tiger");
    compareFileToMessage("./tests/ex3.out", message);
  }

  @Test
  public void ex4() {
    String message = compiler.compile("./tests/ex4.tiger");
    compareFileToMessage("./tests/ex4.out", message);
  }

  @Test
  public void ex5() {
    String message = compiler.compile("./tests/ex5.tiger");
    compareFileToMessage("./tests/ex5.out", message);
  }

  @Test
  public void ex6() {
    String message = compiler.compile("./tests/ex6.tiger");
    compareFileToMessage("./tests/ex6.out", message);
  }

  @Test
  public void ex7() {
    String message = compiler.compile("./tests/ex7.tiger");
    compareFileToMessage("./tests/ex7.out", message);
  }

  @Test
  public void tictactoe() {
    String message = compiler.compile("./tests/ex8.tiger");
    compareFileToMessage("./tests/ex8.out", message);
  }

  String readFile(String fileName) {
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(fileName));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    try {
      StringBuilder sb = new StringBuilder();
      String line = null;
      if (br != null)
        line = br.readLine();

      while (line != null) {
        sb.append(line);
        sb.append("\n");
        line = br.readLine();
      }
      return sb.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
}
