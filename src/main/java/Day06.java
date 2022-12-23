import java.util.List;

import util.Util;


public class Day06 extends EveryDay {

   public static void main( String[] args ) {
      Day06 today = new Day06();
      //      today.testInput = true;
      today.compute(4); // should be 1912
      today.compute(14); // should be 2122
   }

   public Integer charactersTillFirstMarker( final String line, int markerLookupLength ) {
      final StringBuilder buffer = new StringBuilder();
      final List<String> strings = Util.stringToTokenStream(line).toList();

      for ( final String token : strings ) {
         buffer.append(token);
         if ( buffer.length() < markerLookupLength ) {
            continue;
         }

         final int thirdLastPosition = buffer.length() - markerLookupLength;
         final String lastThreeChars = buffer.substring(thirdLastPosition, buffer.length());
         if ( containsNoDuplicates(lastThreeChars) ) {
            break;
         }
      }
      return buffer.length();
   }

   private void compute( int markerLookupLength ) {
      for ( String line : input() ) {
         System.out.println(charactersTillFirstMarker(line, markerLookupLength));
      }
   }

   private boolean containsNoDuplicates( final String lastThreeChars ) {
      final long count = lastThreeChars.chars().distinct().count();
      return lastThreeChars.length() == count;
   }

}
