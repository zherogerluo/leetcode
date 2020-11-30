/**
 * LeetCode #221, medium
 *
 * Given an m x n binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its
 * area.
 *
 * Example 1:
 *
 * Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
 * Output: 4
 *
 * Example 2:
 *
 * Input: matrix = [["0","1"],["1","0"]]
 * Output: 1
 *
 * Example 3:
 *
 * Input: matrix = [["0"]]
 * Output: 0
 *
 * Constraints:
 *
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 300
 * matrix[i][j] is '0' or '1'.
 */

public class MaximalSquare {
    /**
     * Solution 1: Dynamic programming
     *
     * Two-dimensional DP array to remember the side length of the largest square with south-east corner at (i, j),
     * which can be obtained by looking at (i-1, j) and (i, j-1). Basically the new side length should be the minimum
     * of the two, except for the special case where the two lengths are equal, where we need to look at the opposite
     * corner to make sure it is '1'.
     *
     * Time complexity: O(m * n). Space complexity: O(m * n).
     */
    class Solution1 {
        public int maximalSquare(char[][] matrix) {
            final int m = matrix.length, n = matrix[0].length;
            int[][] length = new int[m][n];
            int res = 0;
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (matrix[i][j] == '0') {
                        continue;
                    }
                    int west  = i > 0 ? length[i-1][j] : 0;
                    int north = j > 0 ? length[i][j-1] : 0;
                    length[i][j] = Math.min(west, north) +
                            (west == north ? matrix[i-west][j-north] - '0' : 1);
                    res = Math.max(res, length[i][j]);
                }
            }
            return res * res;
        }
    }
}