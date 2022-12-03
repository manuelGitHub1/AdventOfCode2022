public class Day02 extends EveryDay {

   public static void main( String[] args ) {
      Day02 day02 = new Day02();
//      day02.testInput = true;
      day02.firstPart();
      day02.secondPart();
   }

   private void firstPart() {
      int currentSum = 0;
      for ( String line : this.input() ) {
         final String[] strings = line.split(" ");
         final RockPaperScissors mine = RockPaperScissors.mapToObj(strings[1]);
         final RockPaperScissors theirs = RockPaperScissors.mapToObj(strings[0]);
         currentSum += mine.beats(theirs);
      }
      System.out.println(currentSum);
   }

   private void secondPart() {
      int currentSum = 0;
      for ( String line : this.input() ) {
         final String[] strings = line.split(" ");
         final RockPaperScissors shape = RockPaperScissors.mapToObj(strings[0]);
         currentSum += RockPaperScissors.getDesiredOutput(shape, strings[1]);
      }
      System.out.println(currentSum);
   }

   enum RockPaperScissors {
      ROCK,
      PAPER,
      SCISSORS;

      public static int getDesiredOutput( RockPaperScissors shape, String input ) {
         return switch ( input ) {
            // lose
            case "X" -> 0 + chooseToLoseShape(shape).shapeScore();
            // draw
            case "Y" -> 3 + shape.shapeScore();
            // win
            case "Z" -> 6 + chooseToWinShape(shape).shapeScore();
            default -> Integer.MAX_VALUE;
         };
      }

      public static RockPaperScissors mapToObj( String input ) {
         return switch ( input ) {
            case "A", "X" -> RockPaperScissors.ROCK;
            case "B", "Y" -> RockPaperScissors.PAPER;
            case "C", "Z" -> RockPaperScissors.SCISSORS;
            default -> null;
         };
      }

      private static RockPaperScissors chooseToLoseShape( RockPaperScissors shape ) {
         return switch ( shape ) {
            case ROCK -> RockPaperScissors.SCISSORS;
            case PAPER -> RockPaperScissors.ROCK;
            case SCISSORS -> RockPaperScissors.PAPER;
         };
      }

      private RockPaperScissors chooseToLoseShape(  ) {
         return chooseToLoseShape(this);
      }

      private static RockPaperScissors chooseToWinShape( RockPaperScissors shape ) {
         return switch ( shape ) {
            case ROCK -> RockPaperScissors.PAPER;
            case PAPER -> RockPaperScissors.SCISSORS;
            case SCISSORS -> RockPaperScissors.ROCK;
         };
      }

      public int beats( RockPaperScissors other ) {
         if ( this == other ) {
            return 3 + shapeScore();
         }
         return switch ( this ) {
            case ROCK -> other == chooseToLoseShape() ? 7 : shapeScore();
            case PAPER -> other == chooseToLoseShape() ? 8 : shapeScore();
            case SCISSORS -> other == chooseToLoseShape() ? 9 : shapeScore();
         };
      }

      public int shapeScore() {
         return switch ( this ) {
            case ROCK -> 1;
            case PAPER -> 2;
            case SCISSORS -> 3;
         };
      }

      private RockPaperScissors chooseToWinShape() {
         return chooseToWinShape(this);

      }
   }

}
