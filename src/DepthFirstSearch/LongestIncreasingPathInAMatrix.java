/**
 * LeetCode #329, hard
 *
 * Given an integer matrix, find the length of the longest increasing path.
 *
 * From each cell, you can either move to four directions: left, right, up or down. You may NOT move diagonally or
 * move outside of the boundary (i.e. wrap-around is not allowed).
 *
 * Example 1:
 *
 * Input: nums =
 * [
 *   [9,9,4],
 *   [6,6,8],
 *   [2,1,1]
 * ]
 * Output: 4
 * Explanation: The longest increasing path is [1, 2, 6, 9].
 * Example 2:
 *
 * Input: nums =
 * [
 *   [3,4,5],
 *   [3,2,6],
 *   [2,2,1]
 * ]
 * Output: 4
 * Explanation: The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.
 */

package DepthFirstSearch;

public class LongestIncreasingPathInAMatrix {
    /**
     * Solution 1: DFS with memoization
     *
     * Standard DFS with memoization. Memo is only valid when larger than zero. One trick is to pass last visited
     * value to next recursive call to avoid multiple boundary checks.
     *
     * Time complexity: O(m * n) because each element will only be visited a finite number of times (four) and
     * results with be cached.
     *
     * Space complexity: O(m * n).
     */
    class Solution1 {
        public int longestIncreasingPath(int[][] matrix) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
            final int m = matrix.length, n = matrix[0].length;
            int[][] memo = new int[m][n];
            int longest = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    longest = Math.max(longest, LIP(matrix, i, j, Integer.MIN_VALUE, memo));
                }
            }
            return longest;
        }

        private int LIP(int[][] matrix, int i, int j, int last, int[][] memo) {
            if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length) return 0;
            if (matrix[i][j] <= last) return 0;
            if (memo[i][j] != 0) return memo[i][j];
            int len = LIP(matrix, i+1, j, matrix[i][j], memo);
            len = Math.max(len, LIP(matrix, i-1, j, matrix[i][j], memo));
            len = Math.max(len, LIP(matrix, i, j+1, matrix[i][j], memo));
            len = Math.max(len, LIP(matrix, i, j-1, matrix[i][j], memo));
            len = len + 1;
            memo[i][j] = len;
            return len;
        }
    }
}
