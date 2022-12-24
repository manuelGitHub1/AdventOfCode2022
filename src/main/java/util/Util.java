package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Util {

   public static List<String> fileAsStrings( String filePath ) {
      final Path path = Paths.get(filePath);
      try {
         return Files.readAllLines(path);
      }
      catch ( IOException e ) {
         throw new RuntimeException(e);
      }
   }

   public static List<String> getAoCInput( final String day ) {
      assert day != null;
      return getInput(day, "/input.txt");
   }

   public static List<Integer> getAoCInputAsInts( final String day ) {
      return getAoCInput(day).stream().map(Util::mapToIntOrNull).collect(Collectors.toList());
   }

   public static List<String> getAoCTestInput( final String day ) {
      assert day != null;
      return getInput(day, "/test.txt");
   }

   public static List<Integer> getAoCTestInputAsInts( final String day ) {
      return getAoCTestInput(day).stream().map(Integer::parseInt).collect(Collectors.toList());
   }

   public static List<String> getIntRangeAsList( String start, String end ) {
      return getIntRangeAsList(Integer.parseInt(start), Integer.parseInt(end));
   }

   public static List<String> getIntRangeAsList( int start, int end ) {
      return IntStream.range(start, end + 1).mapToObj(i -> "|" + String.valueOf(i) + "|").toList();
   }

   public static String getIntRangeAsString( String start, String end ) {
      return getIntRangeAsString(Integer.parseInt(start), Integer.parseInt(end));
   }

   public static String getIntRangeAsString( int start, int end ) {
      return "|" + IntStream.range(start, end + 1).mapToObj(String::valueOf).collect(Collectors.joining("|")) + "|";
   }

   public static Stream<String> stringToTokenStream( String line ) {
      return IntStream.range(0, line.length()).mapToObj(i -> "" + line.charAt(i));
   }

   private static List<String> getInput( String day, String s ) {
      return fileAsStrings("src/main/resources/" + day.toLowerCase() + s);
   }

   private static Integer mapToIntOrNull( String input ) {
      if ( input == null || input.isBlank() ) {
         return null;
      }

      return Integer.parseInt(input);
   }
}
