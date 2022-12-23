import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class Day07 extends EveryDay {

   public static void main( String[] args ) {
      Day07 today = new Day07();
      today.testInput = true;
      today.firstPart();
   }

   private void firstPart() {
      final LinkedList<Directory> directories = new LinkedList<>();

      Directory _currentDirectory = null;

      for ( String line : input() ) {
         final Input input = new Input(line);
         if ( input.getType() == Type.cd_down ) {
            final String directoryName = input.getParameterOrNull();
            _currentDirectory = new Directory(directoryName);
            directories.add(_currentDirectory);
         } else if ( input.getType() == Type.directory ) {
            _currentDirectory.getSubDirectories().add(new Directory(input.getParameterOrNull()));
         } else if ( input.getType() == Type.file ) {
            _currentDirectory.addToDirectorySize(input.getFileInfoOrNull().fileSize());
         }
      }




      Collections.reverse(directories);
      for ( Directory directory : directories ) {
         final List<String> subdirectories = directory.getSubDirectories().stream().map(Directory::getDirectoryName).toList();
         directories.stream()
               .filter(d -> subdirectories.contains(d.getDirectoryName()))
               .map(Directory::getDirectorySize)
               .forEach(size -> directory.addToDirectorySize(size));
      }
      Collections.reverse(directories);

      long wantedSize = 100_000L;

      directories.forEach(System.out::println);

      final List<Directory> candidates = directories.stream().filter(d -> d.getDirectorySize() <= wantedSize).toList();

      candidates.forEach(directory -> System.out.println(directory.getDirectoryName() + " " + directory.getDirectorySize()));

      final long sum = candidates.stream().mapToLong(Directory::getDirectorySize).sum();
      System.out.println(sum);

      assert sum > 1_290_008;

      // Look for almost size = 100000

   }

   enum Type {
      cd_down,
      cd_up,
      ls,
      file,
      directory;
   }


   record FileInfo(String name, Long fileSize) {}


   static class Input {

      private final String _input;
      private       Type   _type;

      private FileInfo _fileInfo = null;

      private String _parameter = null;

      public Input( String input ) {
         if ( input == null ) {
            throw new IllegalArgumentException("Input may not be null");
         }
         _input = input;

         final String[] split = input.split(" ");
         final String first = split[0];
         final String second = split[1];
         if ( "$".equals(first) ) {
            final String command = second;
            if ( "cd".equals(command) ) {
               handleCd(split);
            } else if ( "ls".equals(command) ) {
               _type = Type.ls;
            }
         } else {
            if ( split.length != 2 ) {
               throw new IllegalStateException("Input was not parsed correctly or is unknown format: " + input + " was split into " + Arrays.toString(split));
            }
            if ( "dir".equals(first) ) {
               _type = Type.directory;
               _parameter = second;
            } else {
               _type = Type.file;
               final long fileSize = Long.parseLong(first);
               final String fileName = second;
               _fileInfo = new FileInfo(fileName, fileSize);
            }
         }

      }

      public FileInfo getFileInfoOrNull() {
         return _fileInfo;
      }

      public String getInput() {
         return _input;
      }

      public String getParameterOrNull() {
         return _parameter;
      }

      public Type getType() {
         return _type;
      }

      @Override
      public String toString() {
         final StringBuffer sb = new StringBuffer("Input{");
         sb.append("_input='").append(_input).append('\'');
         sb.append(", _type=").append(_type);
         sb.append(", _fileInfo=").append(_fileInfo);
         sb.append(", _parameter='").append(_parameter).append('\'');
         sb.append('}');
         return sb.toString();
      }

      private void handleCd( String[] split ) {
         _parameter = split[2];
         if ( "..".equals(_parameter) ) {
            _type = Type.cd_up;
         } else {
            _type = Type.cd_down;
         }
      }
   }

}
