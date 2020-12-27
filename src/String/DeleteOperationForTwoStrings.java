/**
 * LeetCode #583, medium
 *
 * Given two words word1 and word2, find the minimum number of steps required to make word1 and word2 the same, where
 * in each step you can delete one character in either string.
 *
 * Example 1:
 * Input: "sea", "eat"
 * Output: 2
 * Explanation: You need one step to make "sea" to "ea" and another step to make "eat" to "ea".
 *
 * Note:
 * The length of given words won't exceed 500.
 * Characters in given words can only be lower-case letters.
 */

package String;

import java.util.*;

public class DeleteOperationForTwoStrings {
    /**
     * Solution 1: Depth-first search with memoization
     *
     * This problem is equivalent of finding equal subsequences of max length. Typical DFS with memoization.
     */
    class Solution1 {
        public int minDistance(String word1, String word2) {
            int[][] memo = new int[word1.length()][word2.length()];
            for (int i = 0; i < memo.length; ++i) {
                Arrays.fill(memo[i], -1);
            }
            return helper(word1.toCharArray(), 0, word2.toCharArray(), 0, memo);
        }

        private int helper(char[] c1, int i1, char[] c2, int i2, int[][] memo) {
            if (i1 == c1.length) {
                return c2.length - i2;
            }
            if (i2 == c2.length) {
                return c1.length - i1;
            }
            if (memo[i1][i2] != -1) {
                return memo[i1][i2];
            }
            int res = 0;
            if (c1[i1] == c2[i2]) {
                res = helper(c1, i1+1, c2, i2+1, memo);
            } else {
                res = 1 + Math.min(helper(c1, i1+1, c2, i2, memo), helper(c1, i1, c2, i2+1, memo));
            }
            memo[i1][i2] = res;
            return res;
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * Direct translation from Solution 1 to bottom-up DP. Note the boundary conditions.
     */
    class Solution2 {
        public int minDistance(String word1, String word2) {
            final int m = word1.length(), n = word2.length();
            int[][] memo = new int[m+1][n+1];
            for (int i = 1; i <= m; ++i) {
                memo[i][0] = i;
            }
            for (int j = 1; j <= n; ++j) {
                memo[0][j] = j;
            }
            for (int i = 1; i <= m; ++i) {
                for (int j = 1; j <= n; ++j) {
                    if (word1.charAt(m-i) == word2.charAt(n-j)) {
                        memo[i][j] = memo[i-1][j-1];
                    } else {
                        memo[i][j] = 1 + Math.min(memo[i-1][j], memo[i][j-1]);
                    }
                }
            }
            return memo[m][n];
        }
    }
}
