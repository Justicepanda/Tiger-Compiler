import org.junit.Before;
import org.junit.Test;
import utilities.StringSplitter;

import static org.junit.Assert.*;

public class StringSplitterTests {

  private StringSplitter ss;

  @Before
  public void setUp() {
    ss = new StringSplitter(',');
  }

  @Test
  public void testNormal() {
    String[] exp = {"a","b","c"};
    assertArrayEquals(exp, ss.split("a,b,c"));
  }

  @Test
  public void testEscaped() {
    String[] exp = {"a","b",",","c"};
    assertArrayEquals(exp, ss.split("a,b,\\,,c"));
  }

  @Test
  public void testEscapedEscape() {
    String[] exp = {"a","\\","b",",","c"};
    assertArrayEquals(exp, ss.split("a,\\\\,b,\\,,c"));
  }
}
