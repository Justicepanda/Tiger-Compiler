import frontend.FrontEnd;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parser.Parser;
import scanner.Scanner;
import scanner.TokenDfa;
import scanner.TokenDfaBuilder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.Assert.*;

public class Phase1Tests {

  private FrontEnd frontend;
  private ByteArrayOutputStream outStream;
  private ByteArrayOutputStream errStream;

  @Before
  public void setUp() {
    outStream = new ByteArrayOutputStream();
    errStream = new ByteArrayOutputStream();

    System.setOut(new PrintStream(outStream));
    System.setErr(new PrintStream(errStream));

    Scanner scanner = new Scanner((TokenDfa) new TokenDfaBuilder().buildFrom("TokenDFA.csv"));
    Parser parser = new Parser(scanner, "ParsingTable.csv");
    frontend = new FrontEnd(scanner, parser);

  }

  @After
  public void tearDown() {
    System.setOut(null);
    System.setErr(null);
  }
  private final String ex1Tokens =
          "LET TYPE ID EQ ARRAY LBRACK INTLIT RBRACK OF ID SEMI VAR ID COMMA ID COLON ID ASSIGN INTLIT SEMI VAR ID " +
                  "COMMA ID COLON ID ASSIGN INTLIT SEMI IN FOR ID ASSIGN INTLIT TO INTLIT DO ID ASSIGN ID PLUS ID " +
                  "LBRACK ID RBRACK MULT ID LBRACK ID RBRACK SEMI ENDDO SEMI ID LPAREN ID RPAREN SEMI END ";
  @Test
  public void ex1() {
    assertTrue(frontend.compile("./tests/ex1.tiger", true));
    assertEquals(ex1Tokens, outStream.toString());
  }

  private final String ex2Tokens =
          "LET FUNC ID LPAREN ID COLON ID RPAREN BEGIN ID LPAREN ID RPAREN SEMI END SEMI IN ID LPAREN INTLIT RPAREN " +
                  "SEMI END ";
  @Test
  public void ex2() {
    assertTrue(frontend.compile("./tests/ex2.tiger", true));
    assertEquals(ex2Tokens, outStream.toString());
  }

  private final String ex3Tokens =
          "LET FUNC ID LPAREN ID COLON ID RPAREN COLON ID BEGIN RETURN LPAREN ID RPAREN SEMI END SEMI IN ID LPAREN " +
                  "INTLIT RPAREN SEMI END ";
  @Test
  public void ex3() {
    assertTrue(frontend.compile("./tests/ex3.tiger", true));
    assertEquals(ex3Tokens, outStream.toString());
  }

  private final String ex4Tokens =
          "LET VAR ID COLON ID SEMI IN ID ASSIGN INTLIT SEMI ID LPAREN ID RPAREN SEMI END ";
  private final String ex4ErrorMessage =
          "\nLexical error (line: 2): \"_\" does not begin a valid token.\n";
  @Test
  public void ex4() {
    assertFalse(frontend.compile("./tests/ex4.tiger", true));
    assertEquals(ex4Tokens, outStream.toString());
    assertEquals(ex4ErrorMessage, errStream.toString());
  }

  private final String ex5Tokens =
          "LET VAR ID COMMA ID COLON ID ASSIGN INTLIT SEMI IN IF LPAREN ID EQ ID RPAREN THEN ID ASSIGN ID INTLIT " +
                  "SEMI ENDIF SEMI ID LPAREN ID RPAREN SEMI END ";
  private final String ex5ErrorMessage =
          "\nLexical error (line: 5): \"%\" does not begin a valid token.\n";
  @Test
  public void ex5() {
    assertFalse(frontend.compile("./tests/ex5.tiger", true));
    assertEquals(ex5Tokens, outStream.toString());
    assertEquals(ex5ErrorMessage, errStream.toString());
  }

  private final String ex6Tokens =
          "LET VAR ID COMMA ID COLON ID SEMI IN ID ASSIGN INTLIT SEMI WHILE LPAREN ID LESSER INTLIT RPAREN DO ID PLUS ";
  private final String ex6ErrorMessage =
          "\nParsing error (line 6):                a+ <-- expected '(' or '[' or ':=' \n";
  @Test
  public void ex6() {
    assertFalse(frontend.compile("./tests/ex6.tiger", true));
    assertEquals(ex6Tokens, outStream.toString());
    assertEquals(ex6ErrorMessage, errStream.toString());
  }

  private final String ex7Tokens =
          "LET TYPE ID ASSIGN ";
  private final String ex7ErrorMessage =
          "\nParsing error (line 2):         type int_arr := <-- \":=\" is not a valid token. Expected \"=\".\n";
  @Test
  public void ex7() {
    assertFalse(frontend.compile("./tests/ex7.tiger", true));
    assertEquals(ex7Tokens, outStream.toString());
    assertEquals(ex7ErrorMessage, errStream.toString());
  }

  private final String tictactoeTokens =
          "LET TYPE ID EQ ARRAY LBRACK INTLIT RBRACK OF ID SEMI TYPE ID EQ ARRAY LBRACK INTLIT RBRACK OF ID SEMI VAR" +
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
  @Test
  public void tictactoe() {
    assertTrue(frontend.compile("./tests/tictactoe.tiger", true));
    assertEquals(tictactoeTokens, outStream.toString());
  }
}
