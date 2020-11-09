/**
 * LeetCode #64, medium
 *
 * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right, which minimizes the
 * sum of all numbers along its path.
 *
 * Note: You can only move either down or right at any point in time.
 *
 * Example 1:
 * Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
 * Output: 7
 * Explanation: Because the path 1 → 3 → 1 → 1 → 1 minimizes the sum.
 *
 * Example 2:
 * Input: grid = [[1,2,3],[4,5,6]]
 * Output: 12
 *
 * Constraints:
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 200
 * 0 <= grid[i][j] <= 100
 */

public class MinimumPathSum {
    /**
     * Solution 1: Dynamic programming
     *
     * Typical two-dimensional DP.
     *
     * Time complexity: O(m * n). Space complexity: O(m * n).
     */
    class Solution1 {
        public int minPathSum(int[][] grid) {
            final int m = grid.length, n = grid[0].length;
            int[][] sum = new int[m][n];
            sum[0][0] = grid[0][0];
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    sum[i][j] = grid[i][j] + Math.min(i == 0 ? Integer.MAX_VALUE : sum[i-1][j],
                                                      j == 0 ? Integer.MAX_VALUE : sum[i][j-1]);
                }
            }
            return sum[m-1][n-1];
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * Same as Solution 1 except for using one-dimensional DP array instead of two - Only need to remember last row.
     *
     * Time complexity: O(m * n). Space complexity: O(n).
     */
    class Solution2 {
        public int minPathSum(int[][] grid) {
            final int m = grid.length, n = grid[0].length;
            int[] sum = new int[n];
            sum[0] = grid[0][0];
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    sum[j] = grid[i][j] + Math.min(i == 0 ? Integer.MAX_VALUE : sum[j],
                                                   j == 0 ? Integer.MAX_VALUE : sum[j-1]);
                }
            }
            return sum[n-1];
        }
    }
}