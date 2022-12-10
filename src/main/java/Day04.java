import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Util;


public class Day04 extends EveryDay {

   public static void main( String[] args ) {
      Day04 today = new Day04();
      //      today.testInput = true;
      System.out.println("Overlapping sections in pairs: " + today.firstPart());
      System.out.println("Overlapping sections overall" + today.secondPart());
   }

   Logger _logger = Logger.getLogger(Day04.class.getName());
   protected final Pattern _pattern = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");

   private int firstPart() {
      int sectionsContainingOtherSections = 0;
      for ( String line : input() ) {
         Matcher matcher = _pattern.matcher(line);
         if ( !matcher.matches() ) {
            throw new IllegalStateException("Could not match line: " + line);
         }
         String section1 = Util.getIntRangeAsString(matcher.group(1), matcher.group(2));
         String section2 = Util.getIntRangeAsString(matcher.group(3), matcher.group(4));

         if ( section1.contains(section2) || section2.contains(section1) ) {
            _logger.info("Found containment. Input " + line + " - Sections: " + section1 + " - " + section2);
            sectionsContainingOtherSections++;
         }
      }
      return sectionsContainingOtherSections;
   }

   private int secondPart() {
      final List<String> sections = new ArrayList<>();
      for ( String line : input() ) {
         Matcher matcher = _pattern.matcher(line);
         if ( !matcher.matches() ) {
            throw new IllegalStateException("Could not match line: " + line);
         }
         sections.add(Util.getIntRangeAsString(matcher.group(1), matcher.group(2)));
         sections.add(Util.getIntRangeAsString(matcher.group(3), matcher.group(4)));
      }

      int sectionsContainingOtherSections = 0;

      for ( String section : sections ) {

      }

      return sectionsContainingOtherSections;
   }
}
