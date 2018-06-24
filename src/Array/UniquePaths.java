/**
 * LeetCode #62, medium
 *
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

 The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right
 corner of the grid (marked 'Finish' in the diagram below).

 How many possible unique paths are there?


 Above is a 7 x 3 grid. How many possible unique paths are there?

 Note: m and n will be at most 100.

 Example 1:

 Input: m = 3, n = 2
 Output: 3
 Explanation:
 From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
 1. Right -> Right -> Down
 2. Right -> Down -> Right
 3. Down -> Right -> Right
 Example 2:

 Input: m = 7, n = 3
 Output: 28
 */

package Array;

public class UniquePaths {
    /**
     * Solution 1: Dynamic programming
     *
     * dp[i][j] = dp[i-1][j] + dp[i][j-1]. Could came from up or left.
     *
     * Time complexity: O(m * n). Space complexity: O(m * n).
     */
    class Solution1 {
        public int uniquePaths(int m, int n) {
            int[][] dp = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 || j == 0) dp[i][j] = 1;
                    else dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
            return dp[m-1][n-1];
        }
    }

    /**
     * Solution 2: Math
     *
     * We need to make n + m - 2 moves, in which n - 1 are rightward moves and m - 1 are downward moves. The problem
     * is then to pick m - 1 or from n + m - 2 moves, which is C(n+m-2, m-1). Formulation for C(n, k) is:
     *
     * C(n, k) = n! / [(n-k)! * k!] = (n * n-1 * ... * n-k+1) / (1 * 2 * ... * k)
     *
     * Note: An optimization will be to pick m - 1 or n - 1 whichever is smaller.
     *
     * Time complexity: O(min(n, m)). Space complexity: O(1).
     */
    class Solution2 {
        public int uniquePaths(int m, int n) {
            int N = n + m - 2, k = Math.min(m, n) - 1;
            double res = 1;
            for (int i = 1; i <= k; i++) res = res * (N - i + 1) / i;
            return (int) Math.round(res);
        }
    }
}
