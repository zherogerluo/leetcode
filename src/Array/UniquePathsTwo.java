/**
 * LeetCode #63, medium
 *
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 *
 * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right
 * corner of the grid (marked 'Finish' in the diagram below).
 *
 * Now consider if some obstacles are added to the grids. How many unique paths would there be?
 *
 * An obstacle and empty space is marked as 1 and 0 respectively in the grid.
 *
 * Note: m and n will be at most 100.
 *
 * Example 1:
 *
 * Input:
 * [
 *   [0,0,0],
 *   [0,1,0],
 *   [0,0,0]
 * ]
 * Output: 2
 *
 * Explanation:
 * There is one obstacle in the middle of the 3x3 grid above.
 * There are two ways to reach the bottom-right corner:
 * 1. Right -> Right -> Down -> Down
 * 2. Down -> Down -> Right -> Right
 */

package Array;

public class UniquePathsTwo {
    /**
     * Solution 1: DFS, top-down DP
     *
     * Typical DFS with memoization. The memo matrix needs to be initialized to something like -1. Pay attention to
     * edge cases.
     *
     * Time complexity: O(n^2). Space complexity: O(n^2).
     */
    class Solution1 {
        public int uniquePathsWithObstacles(int[][] obstacleGrid) {
            if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0)
                return 0;
            int m = obstacleGrid.length, n = obstacleGrid[0].length;
            int[][] memo = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    memo[i][j] = -1;
                }
            }
            return move(obstacleGrid, 0, 0, memo);
        }

        private int move(int[][] grid, int i, int j, int[][] memo) {
            if (i >= grid.length || j >= grid[0].length || grid[i][j] == 1) return 0; // always check index range
            if (i == grid.length-1 && j == grid[0].length-1) return 1; // not OR, obstacle could be at edge
            if (memo[i][j] >= 0) return memo[i][j];
            int result = move(grid, i+1, j, memo) + move(grid, i, j+1, memo);
            memo[i][j] = result;
            return result;
        }
    }

    /**
     * Solution 2: Bottom-up DP
     *
     * Note this DP solution progresses from end to start of matrix. Put 1 at [m-1][n-1] as initial condition, then
     * for [i][j] the solution is simply sum of its right and bottom elements (need to check for obstacles). The
     * definition of dp[i][j] is: the number of ways from [i][j] to reach destination.
     *
     * Time complexity: O(n^2). Space complexity: O(n^2).
     */
    class Solution2 {
        public int uniquePathsWithObstacles(int[][] obstacleGrid) {
            if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0)
                return 0;
            int m = obstacleGrid.length, n = obstacleGrid[0].length;
            int[][] results = new int[m][n];
            results[m-1][n-1] = 1;
            for (int i = m-1; i >= 0; i--) {
                for (int j = n-1; j >= 0; j--) {
                    if (obstacleGrid[i][j] == 1) results[i][j] = 0;
                    else if (i != m-1 || j != n-1) {
                        results[i][j] = (i == m-1 ? 0 : results[i+1][j]) +
                                        (j == n-1 ? 0 : results[i][j+1]);
                    }
                }
            }
            return results[0][0];
        }
    }

    /**
     * Solution 3: Bottom-up DP, one-dimensional array
     *
     * From Solution 2 we can further optimize the space complexity. Note that we always use (i+1)-th row as a baseline
     * for i-th row, so we only need to keep results for (i+1)-th row.
     *
     * Time complexity: O(n^2). Space complexity: O(n).
     */
    class Solution3 {
        public int uniquePathsWithObstacles(int[][] obstacleGrid) {
            if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0)
                return 0;
            int m = obstacleGrid.length, n = obstacleGrid[0].length;
            int[] results = new int[n];
            results[n-1] = 1;
            for (int i = m-1; i >= 0; i--) {
                for (int j = n-1; j >= 0; j--) {
                    if (obstacleGrid[i][j] == 1) results[j] = 0;
                    else if (i != m-1 || j != n-1) {
                        results[j] += (j == n-1 ? 0 : results[j+1]);
                    }
                }
            }
            return results[0];
        }
    }
}
