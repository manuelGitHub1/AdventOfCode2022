import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.Util;


public class Day08 extends EveryDay {

   public static void main( String[] args ) {
      Day08 today = new Day08();
      //      today.testInput = true;
      today.firstPart();
      today.secondPart();
   }

   private void firstPart() {
      final List<String> input = input();
      int maxLineNumber = input.size();
      int rowNumber;

      List<Tree> trees = new ArrayList<>();
      for ( String string : input ) {
         final List<String> charsAsStringList = Util.stringToTokenStream(string).toList();
         maxLineNumber--;
         rowNumber = 0;
         for ( String charAsString : charsAsStringList ) {
            final int treeHeight = Integer.parseInt(charAsString);
            final Tree tree = new Tree();
            tree.setLocation(rowNumber, maxLineNumber);
            tree.setTreeHeight(treeHeight);
            trees.add(tree);
            rowNumber++;

         }
      }

      final long count = trees.stream().map(tree -> isTreeVisible(tree, trees)).filter(bool -> bool).count();
      System.out.println(count);
   }

   private boolean isTreeVisible( Tree thisTree, List<Tree> trees ) {

      final List<Tree> biggerTrees = new ArrayList<>();

      for ( Tree otherTree : trees ) {
         if ( otherTree == thisTree ) {
            continue;
         }
         if ( otherTree.getX() == thisTree.getX() || otherTree.getY() == thisTree.getY() ) {
            if ( thisTree.getTreeHeight() <= otherTree.getTreeHeight() ) {
               biggerTrees.add(otherTree);
            }
         }
      }
      final Set<Direction> biggerTreesLocation = new HashSet<>();
      // check the location of the bigger trees
      for ( Tree biggerTree : biggerTrees ) {
         if ( biggerTree.getX() == thisTree.getX() ) {
            biggerTreesLocation.add(biggerTree.getY() > thisTree.getY() ? Direction.north : Direction.south);
         } else if ( biggerTree.getY() == thisTree.getY() ) {
            biggerTreesLocation.add(biggerTree.getX() > thisTree.getX() ? Direction.east : Direction.west);
         } else {
            throw new IllegalArgumentException("Other tree must be on the same row or column as this tree");
         }
      }

      return !biggerTreesLocation.containsAll(Arrays.asList(Direction.values()));
   }

   private void secondPart() {
   }

   enum Direction {
      north,
      south,
      east,
      west
   }


   static class Tree extends Point {

      int _treeHeight;

      public int getTreeHeight() {
         return _treeHeight;
      }

      public void setTreeHeight( int treeHeight ) {
         _treeHeight = treeHeight;
      }

      @SuppressWarnings("StringBufferReplaceableByString")
      @Override
      public String toString() {
         final StringBuilder sb = new StringBuilder("Tree{");
         sb.append("x=").append(x);
         sb.append(", y=").append(y);
         sb.append(", _treeHeight=").append(_treeHeight);
         sb.append('}');
         return sb.toString();
      }
   }

}
