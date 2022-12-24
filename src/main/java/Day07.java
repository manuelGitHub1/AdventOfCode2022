import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;


public class Day07 extends EveryDay {

   public static void main( String[] args ) {
      Day07 today = new Day07();
//      today.testInput = true;
      today.firstPart();
      today.secondPart();
   }

   private static void findCandidates( List<Directory> candidates, LinkedList<Directory> subDirectories, Function<Long, Boolean> function ) {

      for ( Directory subDirectory : subDirectories ) {
         if ( function.apply(subDirectory.getDirectorySize()) ) {
            candidates.add(subDirectory);
         }
         if ( !subDirectory.getSubDirectories().isEmpty() ) {
            findCandidates(candidates, subDirectory.getSubDirectories(), function);
         }
      }
   }

   private void firstPart() {

      final Directory _rootDirectory = getRootDirectory();

      final List<Directory> candidates = new ArrayList<>();
      findCandidates(candidates, _rootDirectory.getSubDirectories(), x -> x <= 100_000L);

      final long sum = candidates.stream().mapToLong(Directory::getDirectorySize).sum();

      if ( testInput ) {
         final int expected = 95437;
         if ( sum != expected ) {
            throw new IllegalStateException("Wrong result. Expected: " + expected + ", actual: " + sum);
         }
      } else {
         if ( sum != 1582412 ) {
            throw new IllegalStateException("Wrong result");
         }
      }

      System.out.println(sum);
   }

   private Directory getRootDirectory() {
      Directory _currentDirectory = null;
      Directory rootDirectory = null;
      for ( String line : input() ) {
         final Input input = new Input(line);
         if ( input.getType() == Type.cd_down ) {
            final String directoryName = input.getParameterOrNull();
            final Directory newDirectory = new Directory(directoryName);
            if ( _currentDirectory == null ) {
               rootDirectory = newDirectory;
            } else {
               newDirectory.setParentDirectory(_currentDirectory);
               _currentDirectory.getSubDirectories().add(newDirectory);
            }
            _currentDirectory = newDirectory;

         } else if ( input.getType() == Type.cd_up ) {
            _currentDirectory = _currentDirectory.getParentDirectory();
         } else if ( input.getType() == Type.file ) {
            _currentDirectory.addToDirectorySize(input.getFileInfoOrNull().fileSize());
         }
      }
      return rootDirectory;
   }

   private void secondPart() {
      final long totalDiskSpace = 70000000L;
      final long spaceNeeded = 30000000L;
      final Directory rootDirectory = getRootDirectory();
      final long rootDirectoryDirectorySize = rootDirectory.getDirectorySize();
      final long spaceToDelete = spaceNeeded - (totalDiskSpace - rootDirectoryDirectorySize);

      final List<Directory> candidates = new ArrayList<>();
      findCandidates(candidates, rootDirectory.getSubDirectories(), x -> x >= spaceToDelete);

      final long sum = candidates.stream().mapToLong(Directory::getDirectorySize).min().getAsLong();

      if ( testInput ) {
         final int expected = 24933642;
         if ( sum != expected ) {
            throw new IllegalStateException("Wrong result. Expected: " + expected + " but actual is: " + sum);
         }
      } else {
         final int expected = 3696336;
         if ( sum != expected ) {
            throw new IllegalStateException("Wrong result. Expected: " + expected + " but actual is: " + sum);
         }
      }

      System.out.println(sum);
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
