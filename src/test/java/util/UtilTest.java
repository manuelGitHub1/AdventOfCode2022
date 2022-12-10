package util;

import junit.framework.TestCase;


public class UtilTest extends TestCase {

   public void testGetIntRangeAsString() {
     assertEquals("|0|1|2|3|",Util.getIntRangeAsString(0, 3));
     assertEquals("|199|200|",Util.getIntRangeAsString(199, 200));
   }
}