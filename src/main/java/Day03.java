import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Day03 extends EveryDay {

   protected static final Map<String, Integer> charToInt = new HashMap<>();

   static {
      buildMapping();
   }

   public static Map.Entry<String, String> divideString( final String line ) {
      if ( line.length() % 2 != 0 ) {
         throw new IllegalStateException("String cannot be divided equal");
      }
      int middle = line.length() / 2;

      final String firstHalf = line.substring(0, middle);
      final String secondHalf = line.substring(middle);
      return Map.entry(firstHalf, secondHalf);
   }

   public static <T> Set<T> findDuplicateBySetAdd( Stream<T> list ) {

      Set<T> items = new HashSet<>();
      return list.filter(n -> !items.add(n)) // Set.add() returns false if the element was already in the set.
            .collect(Collectors.toSet());

   }

   public static void main( String[] args ) {
      Day03 today = new Day03();
//      today.testInput = true;
      today.firstPart();
      today.secondPart();
   }

   private static void buildMapping() {
      int currentValue = 1;
      for ( char c = 'a'; c <= 'z'; ++c ) {
         charToInt.put("" + c, currentValue);
         currentValue++;
      }
      for ( char c = 'A'; c <= 'Z'; ++c ) {
         charToInt.put("" + c, currentValue);
         currentValue++;
      }
      //      charToInt.entrySet().forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));
   }

   private static Stream<String> stringToTokenStream( String line ) {
      return IntStream.range(0, line.length()).mapToObj(i -> "" + line.charAt(i));
   }

   private void firstPart() {
      int sum = 0;
      for ( String line : input() ) {
         final Map.Entry<String, String> entry = divideString(line);
         final String firstPart = entry.getKey();
         final String secondPart = entry.getValue();
         final Stream<String> characterFromFirstHalf = stringToTokenStream(firstPart);
         String foundInSecondHalf = characterFromFirstHalf.filter(secondPart::contains).findFirst().orElse(null);
         final Integer mappingValue = charToInt.get(foundInSecondHalf);
         System.out.println(foundInSecondHalf + " = " + mappingValue);
         sum += mappingValue;
      }
      assert sum == 8515;
      System.out.println(sum);
   }

   private void secondPart() {
      int sum = 0;
      final ElfGroup elfGroup = new ElfGroup();
      for ( String line : input() ) {
         elfGroup.backpacksContent.add(line);
         if ( elfGroup.isFull() ) {
            final String groupSymbol = elfGroup.getGroupSymbol();
            System.out.println("GroupSymbol is: " + groupSymbol);
            sum += charToInt.get(groupSymbol);
            elfGroup.reset();
         }
      }
      System.out.println(sum);

   }

   static class ElfGroup {

      final List<String> backpacksContent = new ArrayList<>(3);

      public String getGroupSymbol() {
         HashMap<String, Integer> stringTokenToBackpackNumber = new HashMap<>();

         for ( String backpackContent : backpacksContent ) {
            stringToTokenStream(backpackContent).distinct()
                  .forEach(token -> stringTokenToBackpackNumber.compute(token, ( key, val ) -> (val == null) ? 1 : val + 1));
         }

         final String string = stringTokenToBackpackNumber.entrySet()
               .stream()
               .filter(e -> e.getValue() == 3)
               .findFirst()
               .map(Map.Entry::getKey)
               .orElse(null);

         return string;
      }

      public boolean isFull() {
         return backpacksContent.size() == 3;
      }

      public void reset() {
         backpacksContent.clear();
      }
   }
}

