import compiler.Compiler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parser.DebugParser;
import parser.Parser;
import scanner.Scanner;
import scanner.TokenDfa;
import scanner.TokenDfaBuilder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.Assert.*;

public class Phase1Tests {

  private Compiler frontend;
  private ByteArrayOutputStream outStream;
  private ByteArrayOutputStream errStream;

  @Before
  public void setUp() {
    outStream = new ByteArrayOutputStream();
    errStream = new ByteArrayOutputStream();

    System.setOut(new PrintStream(outStream));
    System.setErr(new PrintStream(errStream));

    Scanner scanner = new Scanner((TokenDfa) new TokenDfaBuilder().buildFrom("TokenDFA.csv"));
    Parser parser = new DebugParser(scanner, "ParsingTable.csv", "GrammarRules");
    frontend = new compiler.Compiler(scanner, parser);

  }

  @After
  public void tearDown() {
    System.setOut(null);
    System.setErr(null);
  }

  @Test
  public void ex1() {
    assertTrue(frontend.compile("./tests/ex1.tiger"));
    String ex1Tokens = "LET TYPE ID EQ ARRAY LBRACK INTLIT RBRACK OF ID SEMI VAR ID COMMA ID COLON ID ASSIGN INTLIT SEMI VAR ID " +
            "COMMA ID COLON ID ASSIGN INTLIT SEMI IN FOR ID ASSIGN INTLIT TO INTLIT DO ID ASSIGN ID PLUS ID " +
            "LBRACK ID RBRACK MULT ID LBRACK ID RBRACK SEMI ENDDO SEMI ID LPAREN ID RPAREN SEMI END ";
    assertEquals(ex1Tokens, outStream.toString());
  }

  @Test
  public void ex2() {
    assertTrue(frontend.compile("./tests/ex2.tiger"));
    String ex2Tokens = "LET FUNC ID LPAREN ID COLON ID RPAREN BEGIN ID LPAREN ID RPAREN SEMI END SEMI IN ID LPAREN INTLIT RPAREN " +
            "SEMI END ";
    assertEquals(ex2Tokens, outStream.toString());
  }

  @Test
  public void ex3() {
    assertTrue(frontend.compile("./tests/ex3.tiger"));
    String ex3Tokens = "LET FUNC ID LPAREN ID COLON ID RPAREN COLON ID BEGIN RETURN LPAREN ID RPAREN SEMI END SEMI IN ID LPAREN " +
            "INTLIT RPAREN SEMI END ";
    assertEquals(ex3Tokens, outStream.toString());
  }

  @Test
  public void ex4() {
    assertFalse(frontend.compile("./tests/ex4.tiger"));
    String ex4Tokens = "LET VAR ID COLON ID SEMI IN ID ASSIGN INTLIT SEMI ID LPAREN ID RPAREN SEMI END ";
    assertEquals(ex4Tokens, outStream.toString());
    String ex4ErrorMessage = "\nLexical error (line: 2): \"_\" does not begin a valid token.\n";
    assertEquals(ex4ErrorMessage, errStream.toString());
  }

  @Test
  public void ex5() {
    assertFalse(frontend.compile("./tests/ex5.tiger"));
    String ex5Tokens = "LET VAR ID COMMA ID COLON ID ASSIGN INTLIT SEMI IN IF LPAREN ID EQ ID RPAREN THEN ID ASSIGN ID INTLIT " +
            "SEMI ENDIF SEMI ID LPAREN ID RPAREN SEMI END ";
    assertEquals(ex5Tokens, outStream.toString());
    String ex5ErrorMessage = "\nLexical error (line: 5): \"%\" does not begin a valid token.\n";
    assertEquals(ex5ErrorMessage, errStream.toString());
  }

  @Test
  public void ex6() {
    assertFalse(frontend.compile("./tests/ex6.tiger"));
    String ex6Tokens = "LET VAR ID COMMA ID COLON ID SEMI IN ID ASSIGN INTLIT SEMI WHILE LPAREN ID LESSER INTLIT RPAREN DO ID PLUS ";
    assertEquals(ex6Tokens, outStream.toString());
    String ex6ErrorMessage = "Parsing error (line 6):                a+ <-- expected '(' or '[' or ':=' \n";
    assertEquals(ex6ErrorMessage, errStream.toString());
  }

  @Test
  public void ex7() {
    assertFalse(frontend.compile("./tests/ex7.tiger"));
    String ex7Tokens = "LET TYPE ID ASSIGN ";
    assertEquals(ex7Tokens, outStream.toString());
    String ex7ErrorMessage = "Parsing error (line 2):         type int_arr := <-- \":=\" is not a valid token. Expected \"=\".\n";
    assertEquals(ex7ErrorMessage, errStream.toString());
  }

  @Test
  public void tictactoe() {
    assertTrue(frontend.compile("./tests/tictactoe.tiger"));
    String tictactoeTokens = "LET TYPE ID EQ ARRAY LBRACK INTLIT RBRACK OF ID SEMI TYPE ID EQ ARRAY LBRACK INTLIT RBRACK OF ID SEMI VAR" +
            " ID COLON ID SEMI VAR ID COLON ID SEMI VAR ID COLON ID ASSIGN STRLIT SEMI VAR ID COLON ID SEMI VAR" +
            " ID COLON ID SEMI VAR ID COMMA ID COMMA ID COMMA ID COLON ID SEMI VAR ID COLON ID ASSIGN INTLIT" +
            " SEMI VAR ID COLON ID ASSIGN INTLIT SEMI VAR ID COLON ID SEMI VAR ID COLON ID SEMI VAR ID COLON ID" +
            " ASSIGN INTLIT SEMI VAR ID COLON ID SEMI VAR ID COLON ID SEMI FUNC ID LPAREN ID COLON ID RPAREN" +
            " BEGIN ID LPAREN STRLIT RPAREN SEMI FOR ID ASSIGN INTLIT TO INTLIT DO ID LPAREN STRLIT RPAREN SEMI" +
            " FOR ID ASSIGN INTLIT TO INTLIT DO IF ID LBRACK ID MULT INTLIT PLUS ID RBRACK EQ INTLIT THEN ID" +
            " LPAREN STRLIT RPAREN SEMI ELSE IF ID LBRACK ID MULT INTLIT PLUS ID RBRACK EQ INTLIT THEN ID" +
            " LPAREN STRLIT RPAREN SEMI ELSE IF ID LBRACK ID MULT INTLIT PLUS ID RBRACK NEQ MINUS INTLIT THEN" +
            " ID LPAREN ID MULT INTLIT PLUS ID MINUS INTLIT RPAREN SEMI ELSE ID LPAREN STRLIT RPAREN SEMI ENDIF" +
            " SEMI ENDIF SEMI ENDIF SEMI IF ID NEQ INTLIT THEN ID LPAREN STRLIT RPAREN SEMI ENDIF SEMI ENDDO" +
            " SEMI IF ID NEQ INTLIT THEN ID LPAREN STRLIT RPAREN SEMI ELSE ID LPAREN STRLIT RPAREN SEMI ENDIF" +
            " SEMI ENDDO SEMI END SEMI FUNC ID LPAREN RPAREN BEGIN ID LPAREN STRLIT RPAREN SEMI ID LPAREN ID" +
            " RPAREN SEMI END SEMI FUNC ID LPAREN ID COLON ID RPAREN BEGIN ID ASSIGN ID LPAREN STRLIT COMMA ID" +
            " RPAREN SEMI ID ASSIGN ID LPAREN ID COMMA STRLIT RPAREN SEMI ID LPAREN ID RPAREN SEMI WHILE INTLIT" +
            " DO ID ASSIGN ID LPAREN RPAREN SEMI ID ASSIGN ID LPAREN ID RPAREN SEMI ID ASSIGN ID MINUS ID SEMI" +
            " IF ID GREATEREQ INTLIT AND ID LESSEREQ INTLIT THEN RETURN ID MINUS INTLIT SEMI ELSE ID LPAREN" +
            " STRLIT RPAREN SEMI ID LPAREN RPAREN SEMI ENDIF SEMI ENDDO SEMI END SEMI FUNC ID LPAREN RPAREN" +
            " BEGIN FOR ID ASSIGN INTLIT TO INTLIT DO ID LBRACK ID MINUS INTLIT RBRACK ASSIGN ID SEMI ENDDO" +
            " SEMI END SEMI FUNC ID LPAREN RPAREN BEGIN ID LBRACK INTLIT RBRACK ASSIGN INTLIT SEMI ID LBRACK" +
            " INTLIT RBRACK ASSIGN INTLIT SEMI ID LBRACK INTLIT RBRACK ASSIGN INTLIT SEMI ID LBRACK INTLIT" +
            " RBRACK ASSIGN INTLIT SEMI ID LBRACK INTLIT RBRACK ASSIGN INTLIT SEMI ID LBRACK INTLIT RBRACK" +
            " ASSIGN INTLIT SEMI ID LBRACK INTLIT RBRACK ASSIGN INTLIT SEMI ID LBRACK INTLIT RBRACK ASSIGN" +
            " INTLIT SEMI ID LBRACK INTLIT RBRACK ASSIGN INTLIT SEMI ID LBRACK INTLIT RBRACK ASSIGN INTLIT" +
            " SEMI ID LBRACK INTLIT RBRACK ASSIGN INTLIT SEMI ID LBRACK INTLIT RBRACK ASSIGN INTLIT SEMI ID" +
            " LBRACK INTLIT RBRACK ASSIGN INTLIT SEMI ID LBRACK INTLIT RBRACK ASSIGN INTLIT SEMI ID LBRACK" +
            " INTLIT RBRACK ASSIGN INTLIT SEMI ID LBRACK INTLIT RBRACK ASSIGN INTLIT SEMI ID LBRACK INTLIT" +
            " RBRACK ASSIGN INTLIT SEMI ID LBRACK INTLIT RBRACK ASSIGN INTLIT SEMI ID LBRACK INTLIT RBRACK" +
            " ASSIGN INTLIT SEMI ID LBRACK INTLIT RBRACK ASSIGN INTLIT SEMI ID LBRACK INTLIT RBRACK ASSIGN" +
            " INTLIT SEMI ID LBRACK INTLIT RBRACK ASSIGN INTLIT SEMI ID LBRACK INTLIT RBRACK ASSIGN INTLIT" +
            " SEMI ID LBRACK INTLIT RBRACK ASSIGN INTLIT SEMI END SEMI FUNC ID LPAREN ID COLON ID RPAREN BEGIN" +
            " FOR ID ASSIGN INTLIT TO INTLIT DO IF LPAREN ID LBRACK ID LBRACK ID MULT INTLIT RBRACK MINUS" +
            " INTLIT RBRACK EQ ID LBRACK ID LBRACK ID MULT INTLIT PLUS INTLIT RBRACK MINUS INTLIT RBRACK" +
            " RPAREN AND LPAREN ID LBRACK ID LBRACK ID MULT INTLIT PLUS INTLIT RBRACK MINUS INTLIT RBRACK EQ" +
            " ID LBRACK ID LBRACK ID MULT INTLIT PLUS INTLIT RBRACK MINUS INTLIT RBRACK RPAREN THEN RETURN ID" +
            " LBRACK ID LBRACK ID MULT INTLIT RBRACK MINUS INTLIT RBRACK SEMI ENDIF SEMI ENDDO SEMI RETURN" +
            " MINUS INTLIT SEMI END SEMI FUNC ID LPAREN ID COLON ID COMMA ID COLON ID RPAREN BEGIN ID LPAREN" +
            " ID RPAREN SEMI ID LPAREN ID RPAREN SEMI ID LPAREN INTLIT RPAREN SEMI END SEMI FUNC ID LPAREN" +
            " RPAREN BEGIN ID LPAREN RPAREN SEMI ID LPAREN RPAREN SEMI ID LPAREN RPAREN SEMI ID ASSIGN INTLIT" +
            " SEMI ID ASSIGN INTLIT SEMI WHILE ID NEQ INTLIT DO ID LPAREN ID RPAREN SEMI ID ASSIGN STRLIT SEMI" +
            " ID ASSIGN ID PLUS INTLIT SEMI ID ASSIGN ID LPAREN ID RPAREN SEMI ID ASSIGN ID LPAREN ID COMMA ID" +
            " RPAREN SEMI ID LPAREN ID RPAREN SEMI ID ASSIGN ID DIV INTLIT MULT INTLIT SEMI IF ID EQ ID THEN ID" +
            " ASSIGN STRLIT SEMI ELSE ID ASSIGN STRLIT SEMI ENDIF SEMI ID ASSIGN ID LPAREN ID RPAREN SEMI WHILE" +
            " ID LBRACK ID RBRACK NEQ MINUS INTLIT DO ID LPAREN STRLIT RPAREN SEMI ID ASSIGN ID LPAREN ID" +
            " RPAREN SEMI ENDDO SEMI IF ID EQ STRLIT THEN ID LBRACK ID RBRACK ASSIGN INTLIT SEMI ELSE ID LBRACK" +
            " ID RBRACK ASSIGN INTLIT SEMI ENDIF SEMI ID ASSIGN ID PLUS INTLIT SEMI IF ID GREATER INTLIT THEN" +
            " ID ASSIGN ID LPAREN ID RPAREN SEMI IF ID NEQ MINUS INTLIT THEN ID ASSIGN STRLIT SEMI IF ID EQ" +
            " INTLIT THEN ID ASSIGN ID LPAREN ID COMMA STRLIT RPAREN SEMI ELSE ID ASSIGN ID LPAREN ID COMMA" +
            " STRLIT RPAREN SEMI ENDIF SEMI ID ASSIGN ID LPAREN ID COMMA STRLIT RPAREN SEMI ID LPAREN ID COMMA" +
            " ID RPAREN SEMI ELSE IF ID EQ INTLIT THEN ID ASSIGN STRLIT SEMI ID LPAREN ID COMMA ID RPAREN SEMI" +
            " ENDIF SEMI ENDIF SEMI ENDIF SEMI ENDDO SEMI END SEMI IN ID LPAREN RPAREN SEMI END ";
    assertEquals(tictactoeTokens, outStream.toString());
  }
}
