/**
 * LeetCode #827, hard
 *
 * In a 2D grid of 0s and 1s, we change at most one 0 to a 1.
 *
 * After, what is the size of the largest island? (An island is a 4-directionally connected group of 1s).
 *
 * Example 1:
 *
 * Input: [[1, 0], [0, 1]]
 * Output: 3
 * Explanation: Change one 0 to 1 and connect two 1s, then we get an island with area = 3.
 *
 * Example 2:
 *
 * Input: [[1, 1], [1, 0]]
 * Output: 4
 * Explanation: Change the 0 to 1 and make the island bigger, only one island with area = 4.
 *
 * Example 3:
 *
 * Input: [[1, 1], [1, 1]]
 * Output: 4
 * Explanation: Can't change any 0 to 1, only one island with area = 4.
 *
 * Notes:
 *
 * 1 <= grid.length = grid[0].length <= 50.
 * 0 <= grid[i][j] <= 1.
 */

public class MakingALargeIsland {
    /**
     * Solution 1: Depth-first search
     *
     * Use DFS to expand the island when we see 1. Each island will have the same parent, the parent element will have
     * a value denoting the island area. The encoding of value: v < 0 for indices (-index-1, starting from (0,0)); v =
     * 0 or 1 for original unvisited, v > 1 for area (area+1 to distinguish from 1).
     *
     * Time complexity: O(m*n) since we visit each element at most twice. Space complexity: O(m*n) worse case for stack.
     */
    class Solution {
        public int largestIsland(int[][] grid) {
            final int m = grid.length, n = grid[0].length;
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (grid[i][j] == 1) {
                        expand(grid, i, j, -(i*m+j)-1);
                    }
                }
            }
            int max = 0;
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (grid[i][j] == 0) {
                        int north = parent(grid, i-1, j);
                        int south = parent(grid, i+1, j);
                        int west = parent(grid, i, j-1);
                        int east = parent(grid, i, j+1);
                        int res = north == -1 ? 1 : grid[north/m][north%m];
                        // stuff below can be replaced by hashset logic
                        if (south != north) {
                            res += south == -1 ? 0 : grid[south/m][south%m] - 1;
                        }
                        if (west != north && west != south) {
                            res += west == -1 ? 0 : grid[west/m][west%m] - 1;
                        }
                        if (east != north && east != south && east != west) {
                            res += east == -1 ? 0 : grid[east/m][east%m] - 1;
                        }
                        max = Math.max(max, res);
                    }
                }
            }
            // max == 0 means the matrix has all ones
            return max == 0 ? m*n : max;
        }

        // expands island with given parent, increments area
        private void expand(int[][] grid, int i, int j, int parent) {
            final int m = grid.length, n = grid[0].length;
            if (i < 0 || i >= m || j < 0 || j >= n) {
                return;
            }
            if (grid[i][j] <= 0) {
                return;
            }
            if (grid[i][j] == 1) {
                int ii = (-parent-1)/m, jj = (-parent-1)%m;
                if (ii == i && jj == j) {
                    // this is the parent, area+1 to distinguish from unvisited, otherwise will overflow stack
                    grid[i][j] = 2;
                } else {
                    grid[i][j] = parent;
                    ++grid[ii][jj];
                }
                expand(grid, i-1, j, parent);
                expand(grid, i+1, j, parent);
                expand(grid, i, j-1, parent);
                expand(grid, i, j+1, parent);
            }
        }

        // find parent, return the index, or -1 if none
        private int parent(int[][] grid, int i, int j) {
            final int m = grid.length, n = grid[0].length;
            if (i < 0 || i >= m || j < 0 || j >= n) {
                return -1;
            }
            if (grid[i][j] == 0) {
                return -1;
            }
            if (grid[i][j] > 0) {
                return i*m+j;
            }
            return parent(grid, (-grid[i][j]-1)/m, (-grid[i][j]-1)%m);
        }
    }
}
