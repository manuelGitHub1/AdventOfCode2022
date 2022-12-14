import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Day01 extends EveryDay {

   public static void main( String[] args ) {
      Day01 day01 = new Day01();
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

}
