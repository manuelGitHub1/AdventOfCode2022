import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Day05 extends EveryDay {

   public static void main( String[] args ) {
      Day05 today = new Day05();
      //            today.testInput = true;
      System.out.println("Crates on top spell (should be JCMHLVGMG): " + today.compute());
      today.keepOrder = true;
      System.out.println("Crates on top spell (should be LVMRWSSPZ): " + today.compute());
   }

   private boolean keepOrder = false;

   public void fillStacks( List<LinkedList<String>> stacks, String line ) {
      final Pattern pattern = Pattern.compile("\\[(.*?)]");
      final Matcher matcher = pattern.matcher(line);
      //         System.out.println("Input: " + line + " result: " + matcher.find() + " " + matcher.groupCount() + " " + matcher.group(1));
      int stackNumber = 0;
      while ( matcher.find() ) {
         stackNumber++;
         final String group = matcher.group(1);
         if ( group.isBlank() ) {
            continue;
         }
         stacks.get(stackNumber).add(group);
      }
   }

   private String compute() {
      List<Command> commands = new ArrayList<>();
      List<LinkedList<String>> stacks = new ArrayList<>();
      boolean isFirstLine = true;
      boolean stopLineFound = false;
      for ( String line : input() ) {
         if ( isFirstLine ) {
            prepareEmptyStacks(stacks, line);
            isFirstLine = false;
         }
         if ( "STOP".equals(line) ) {
            stopLineFound = true;
            continue;
         }
         if ( !stopLineFound ) {
            fillStacks(stacks, line);
         } else {
            final Command command = fillCommands(line);
            commands.add(command);
         }
      }

      stacks.forEach(Collections::reverse);

      executeCommands(commands, stacks);

      return stacks.stream().map(LinkedList::peekLast).filter(Objects::nonNull).collect(Collectors.joining());
   }

   private void executeCommands( List<Command> commands, List<LinkedList<String>> stacks ) {
      List<String> boxesToMove = new ArrayList<>();
      for ( Command command : commands ) {
         final LinkedList<String> targetStack = stacks.get(command.stackNumberTarget);
         final LinkedList<String> sourceStack = stacks.get(command.stackNumberOrigin);
         for ( int i = command.boxCount; i > 0; i-- ) {
            boxesToMove.add(sourceStack.removeLast());
            if ( !keepOrder ) {
               boxesToMove.forEach(targetStack::addLast);
               boxesToMove.clear();
            }
         }
         if ( keepOrder ) {
            Collections.reverse(boxesToMove);
            boxesToMove.forEach(targetStack::addLast);
            boxesToMove.clear();
         }
      }
   }

   private Command fillCommands( String line ) {

      // move 1 from 2 to 1

      final Pattern pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d)");
      final Matcher matcher = pattern.matcher(line);
      if ( !matcher.matches() || matcher.groupCount() < 3 ) {
         throw new IllegalStateException("Command pattern didn't match for line: " + line);
      }

      final Command command = new Command();
      command.boxCount = Integer.parseInt(matcher.group(1));
      command.stackNumberOrigin = Integer.parseInt(matcher.group(2));
      command.stackNumberTarget = Integer.parseInt(matcher.group(3));
      return command;
   }

   private void prepareEmptyStacks( final List<LinkedList<String>> stacks, String line ) {
      Map<Character, Long> frequency = line.chars().mapToObj(c -> (char)c).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
      final Long neededStacks = frequency.get('[');
      for ( long i = 0; i <= neededStacks; i++ ) {
         // stack with index=0 is just used as filler, to be able to address the frist stack with index=1 later
         stacks.add(new LinkedList<>());
      }
   }

   private static class Command {

      int stackNumberOrigin;
      int boxCount;
      int stackNumberTarget;

      @Override
      public String toString() {
         return "Command{" + "stackNumberOrigin=" + stackNumberOrigin + ", stackNumberTarget=" + stackNumberTarget + ", boxCount=" + boxCount + '}';
      }
   }
}
