import junit.framework.TestCase;


public class Day08Test extends TestCase {

   public void testTree_scenicScore() {
      final Day08.Tree tree = new Day08.Tree();

      tree.addScenicScore(1);
      tree.addScenicScore(2);
      tree.addScenicScore(3);

      assertEquals(6, tree.getScenicScoreResult());
   }

}