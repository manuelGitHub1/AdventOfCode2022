import java.util.LinkedList;


class Directory {

   private final String                _directoryName;
   private final LinkedList<Directory> _subDirectories = new LinkedList<>();

   private long directorySize;

   Directory( String directoryName ) {_directoryName = directoryName;}

   public void addToDirectorySize( long value ) {
      directorySize += value;
   }

   public String getDirectoryName() {
      return _directoryName;
   }

   public long getDirectorySize() {
      return directorySize;
   }

   public LinkedList<Directory> getSubDirectories() {
      return _subDirectories;
   }

   @Override
   public String toString() {
      final StringBuffer sb = new StringBuffer("Directory{");
      sb.append("_directoryName='").append(_directoryName).append('\'');
      sb.append(", _subDirectories=").append(_subDirectories);
      sb.append(", directorySize=").append(directorySize);
      sb.append('}');
      return sb.toString();
   }
}
