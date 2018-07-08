/**
 * LeetCode #200, medium
 *
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water
 * and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid
 * are all surrounded by water.

 Example 1:

 Input:
 11110
 11010
 11000
 00000

 Output: 1
 Example 2:

 Input:
 11000
 11000
 00100
 00011

 Output: 3
 */

package DepthFirstSearch;

public class NumberOfIslands {
    /**
     * Solution 1: DFS
     *
     * Typical DFS, mark all surrounding land as water (or something else) until it sees water.
     *
     * Time complexity: O(m * n). Space complexity: O(m + n) (call stack)
     */
    class Solution1 {
        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
            int res = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == '1') {
                        res++;
                        expand(grid, i, j);
                    }
                }
            }
            return res;
        }

        private void expand(char[][] grid, int i, int j) {
            if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) return;
            if (grid[i][j] == '1') {
                grid[i][j] = '0';
                expand(grid, i+1, j);
                expand(grid, i-1, j);
                expand(grid, i, j+1);
                expand(grid, i, j-1);
            }
        }
    }
}
