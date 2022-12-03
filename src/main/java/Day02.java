import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Day02 extends EveryDay {

   public static void main( String[] args ) {
      Day02 day01 = new Day02();
      day01.firstPart();
      day01.secondPart();
      //      day01.secondPart(Util.getAoCInputAsInts(Day01.class.getName()));
   }

   private void firstPart() {
      int highestSum = 0;
      int currentSum = 0;
      for ( String line : this.input() ) {
         if ( line.isBlank() ) {
            highestSum = Math.max(highestSum, currentSum);
            currentSum = 0;
            continue;
         }
         currentSum += Integer.parseInt(line);
      }
      System.out.println(highestSum);
   }

   private void secondPart() {
      List<Integer> allCalories = new ArrayList<>();
      int currentSum = 0;
      for ( String line : this.input() ) {
         if ( line.isBlank() ) {
            allCalories.add(currentSum);
            currentSum = 0;
            continue;
         }
         currentSum += Integer.parseInt(line);
      }
      allCalories.sort(Comparator.reverseOrder());
      System.out.println(allCalories.get(0) + allCalories.get(1) + allCalories.get(2));
   }

   enum RockPaperScissors {
      ROCK,
      PAPER,
      SCISSORS;


      public int beats( RockPaperScissors other ) {
         if ( this == other ) {
            return 3;
         }
         return switch ( this ) {
            case ROCK -> other == SCISSORS ? 6 : 0;
            case PAPER -> other == ROCK ? 6 : 0;
            case SCISSORS -> other == PAPER ? 6 : 0;
         };
      }
   }

}
