/**
 * LeetCode #695, easy
 *
 * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected
 * 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.
 *
 * Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)
 *
 * Example 1:
 * [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,1,1,0,1,0,0,0,0,0,0,0,0],
 *  [0,1,0,0,1,1,0,0,1,0,1,0,0],
 *  [0,1,0,0,1,1,0,0,1,1,1,0,0],
 *  [0,0,0,0,0,0,0,0,0,0,1,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 * Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.
 *
 * Example 2:
 * [[0,0,0,0,0,0,0,0]]
 * Given the above grid, return 0.
 *
 * Note: The length of each dimension in the given grid does not exceed 50.
 */

package Array;

public class MaxAreaOfIsland {
    /**
     * Solution 1: DFS
     *
     * Typical DFS, but modifies the original array for memoization (marking) purpose.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution1 {
        public int maxAreaOfIsland(int[][] grid) {
            if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
            int max = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 1) max = Math.max(max, area(grid, i, j));
                }
            }
            return max;
        }

        private int area(int[][] grid, int i, int j) {
            if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == 0)
                return 0;
            grid[i][j] = 0;
            return 1 + area(grid, i+1, j) + area(grid, i-1, j) + area(grid, i, j+1) + area(grid, i, j-1);
        }
    }
}
