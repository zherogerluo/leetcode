/**
 * LeetCode #463, easy
 *
 * You are given a map in form of a two-dimensional integer grid where 1 represents land and 0 represents water. Grid
 * cells are connected horizontally/vertically (not diagonally). The grid is completely surrounded by water, and
 * there is exactly one island (i.e., one or more connected land cells). The island doesn't have "lakes" (water
 * inside that isn't connected to the water around the island). One cell is a square with side length 1. The grid is
 * rectangular, width and height don't exceed 100. Determine the perimeter of the island.
 *
 * Example:
 *
 * [[0,1,0,0],
 *  [1,1,1,0],
 *  [0,1,0,0],
 *  [1,1,0,0]]
 *
 * Answer: 16
 * Explanation: The perimeter is the 16 yellow stripes in the image below:
 */

package HashTable;

public class IslandPerimeter {
    /**
     * Solution 1:
     *
     * Iterate through land and count adjacent water cells. Trivial.
     *
     * Time complexity: O(m * n). Space complexity: O(1).
     */
    class Solution1 {
        public int islandPerimeter(int[][] grid) {
            int res = 0;
            if (grid == null || grid.length == 0) return 0;
            final int m = grid.length, n = grid[0].length;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 0) continue;
                    if (i == 0 || grid[i-1][j] == 0) res++;
                    if (i == m-1 || grid[i+1][j] == 0) res++;
                    if (j == 0 || grid[i][j-1] == 0) res++;
                    if (j == n-1 || grid[i][j+1] == 0) res++;
                }
            }
            return res;
        }
    }

    /**
     * Solution 2:
     *
     * Another one with same idea.
     */
    class Solution2 {
        public int islandPerimeter(int[][] grid) {
            int perimeter = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 1) perimeter += countSurroundingWater(grid, i, j);
                }
            }
            return perimeter;
        }

        private int countSurroundingWater(int[][] grid, int i, int j) {
            int count = 0;
            if (i < grid.length - 1) count += grid[i+1][j];
            if (i > 0) count += grid[i-1][j];
            if (j < grid[0].length - 1) count += grid[i][j+1];
            if (j > 0) count += grid[i][j-1];
            return 4 - count;
        }
    }
}
