import java.util.Map;

import junit.framework.TestCase;


public class Day03Test extends TestCase {

   public void testDivideString() {
      final Map.Entry<String, String> abcd = Day03.divideString("ABCD");
      assertEquals("AB", abcd.getKey());
      assertEquals("CD", abcd.getValue());
   }
}