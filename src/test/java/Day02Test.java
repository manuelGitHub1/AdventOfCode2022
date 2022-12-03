import org.junit.Test;

import junit.framework.TestCase;


public class Day02Test extends TestCase {
   @Test
   public void testEnum_draw(){
      for ( Day02.RockPaperScissors rockPaperScissors : Day02.RockPaperScissors.values() ) {
         final int result = rockPaperScissors.beats(rockPaperScissors);
         assertEquals(3, result);
      }
   }

   @Test
   public void testEnum_wins(){
      assertEquals(6, Day02.RockPaperScissors.ROCK.beats(Day02.RockPaperScissors.SCISSORS));
      assertEquals(6, Day02.RockPaperScissors.PAPER.beats(Day02.RockPaperScissors.ROCK));
      assertEquals(6, Day02.RockPaperScissors.SCISSORS.beats(Day02.RockPaperScissors.PAPER));
   }

   @Test
   public void testEnum_losses(){
      assertEquals(0, Day02.RockPaperScissors.ROCK.beats(Day02.RockPaperScissors.PAPER));
      assertEquals(0, Day02.RockPaperScissors.PAPER.beats(Day02.RockPaperScissors.SCISSORS));
      assertEquals(0, Day02.RockPaperScissors.SCISSORS.beats(Day02.RockPaperScissors.ROCK));
   }
}