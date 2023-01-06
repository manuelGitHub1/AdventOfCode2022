import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import util.Util;


public class Day08 extends EveryDay {

   public static void main( String[] args ) {
      Day08 today = new Day08();
      //      today.testInput = true;
      today.firstPart();
      today.secondPart();
   }

   private static void setScenicScoresForDirection( final List<Tree> trees, final Tree visibleTree, final Consumer<Point> pointModifirer ) {
      int scenicScore = 0;
      final Point lookupLocation = new Point(visibleTree.getLocation());
      while ( true ) {
         pointModifirer.accept(lookupLocation);
         final Tree otherTree = trees.stream().filter(tree -> tree.getLocation().equals(lookupLocation)).findFirst().orElse(null);
         if ( otherTree == null ) {
            visibleTree.addScenicScore(scenicScore);
            return;
         }

         if ( otherTree.getTreeHeight() < visibleTree.getTreeHeight() ) {
            scenicScore++;
         } else if ( otherTree.getTreeHeight() >= visibleTree.getTreeHeight() ) {
            scenicScore++;
            visibleTree.addScenicScore(scenicScore);
            return;
         }
      }
   }

   private void firstPart() {
      final List<Tree> visibleTrees = getVisibleTrees();
      assert visibleTrees.size() == 1690;
      System.out.println(visibleTrees.size());
   }

   private List<Tree> getTrees() {
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
      return trees;
   }

   private List<Tree> getVisibleTrees() {
      final List<Tree> trees = getTrees();
      return trees.stream().filter(tree -> isTreeVisible(tree, trees)).toList();
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
      final List<Tree> trees1 = getTrees();
      final List<Tree> trees2 = getTrees();
      final List<Consumer<Point>> modifiers = List.of( //
            p -> p.setLocation(p.x, p.y + 1), //
            p -> p.setLocation(p.x - 1, p.y), //
            p -> p.setLocation(p.x, p.y - 1), //
            p -> p.setLocation(p.x + 1, p.y));

      for ( Tree visibleTree : trees1 ) {
         for ( final Consumer<Point> modifier : modifiers ) {
            setScenicScoresForDirection(trees2, visibleTree, modifier);
         }
      }

      final Tree bestTree = trees1.stream().max(Comparator.comparingInt(Tree::getScenicScoreResult)).orElse(null);
      final int scenicScoreResult = bestTree.getScenicScoreResult();
      assert scenicScoreResult == 535680;
      System.out.println(scenicScoreResult);
   }

   enum Direction {
      north,
      south,
      east,
      west
   }


   static class Tree extends Point {

      int _treeHeight;

      List<Integer> _scenicScores = new ArrayList<>();

      public void addScenicScore( int scenicScore ) {
         _scenicScores.add(scenicScore);
      }

      public int getScenicScoreResult() {
         return _scenicScores.stream().mapToInt(Integer::intValue).reduce(1, ( a, b ) -> a * b);
      }

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
         sb.append(", _scenicScoreValues=").append(_scenicScores);
         sb.append('}');
         return sb.toString();
      }
   }

}
