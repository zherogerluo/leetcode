/**
 * LeetCode #115, hard
 *
 * Given two strings s and t, return the number of distinct subsequences of s which equals t.
 *
 * A string's subsequence is a new string formed from the original string by deleting some (can be none) of the
 * characters without disturbing the relative positions of the remaining characters. (i.e., "ACE" is a subsequence of
 * "ABCDE" while "AEC" is not).
 *
 * It's guaranteed the answer fits on a 32-bit signed integer.
 *
 * Example 1:
 *
 * Input: s = "rabbbit", t = "rabbit"
 * Output: 3
 * Explanation:
 * As shown below, there are 3 ways you can generate "rabbit" from S.
 * rabb[b]it
 * rab[b]bit
 * ra[b]bbit
 *
 * Example 2:
 *
 * Input: s = "babgbag", t = "bag"
 * Output: 5
 * Explanation:
 * As shown below, there are 5 ways you can generate "bag" from S.
 * ba[b]g[bag]
 * ba[bgba]g
 * b[abgb]ag
 * [ba]b[gb]ag
 * [babg]bag
 *
 * Constraints:
 *
 * 0 <= s.length, t.length <= 1000
 * s and t consist of English letters.
 */

package String;

import java.util.*;

public class DistinctSubsequences {
    /**
     * Solution 1: Depth-first search with memoization
     *
     * Use a hash map for memoization keyed by encoded string s-t. The edge cases are a bit tricky: If t is empty, we
     * already consumed t thus found a valid subsequence of s, therefore we should return 1. Otherwise if s is empty,
     * we fail to find one and should return 0.
     *
     * Time complexity: O(m * n), consider the number of times we fill in the memoization storage.
     * Space complexity: O(m * n).
     */
    class Solution1 {
        public int numDistinct(String s, String t) {
            return helper(s, t, new HashMap<>());
        }

        private int helper(String s, String t, Map<String, Integer> memo) {
            if (t.isEmpty()) {
                return 1;
            }
            if (s.isEmpty()) {
                return 0;
            }
            String encoded = new StringBuilder(s).append("-").append(t).toString();
            if (memo.containsKey(encoded)) {
                return memo.get(encoded);
            }
            int res = 0;
            char first = t.charAt(0);
            for (int i = 0; i < s.length(); ++i) {
                if (s.charAt(i) == first) {
                    res += helper(s.substring(i+1), t.substring(1), memo);
                }
            }
            memo.put(encoded, res);
            return res;
        }
    }

    /**
     * Solution 2: DFS with memoization
     *
     * Optimized from solution 1, without using substring and map.
     */
    class Solution2 {
        public int numDistinct(String s, String t) {
            char[] s_chars = s.toCharArray();
            char[] t_chars = t.toCharArray();
            int[][] memo = new int[s_chars.length][t_chars.length];
            for (int i = 0; i < s_chars.length; ++i) {
                Arrays.fill(memo[i], -1);
            }
            return helper(s_chars, 0, t_chars, 0, memo);
        }

        private int helper(char[] s, int i, char[] t, int j, int[][] memo) {
            if (j == t.length) {
                return 1;
            }
            if (i == s.length) {
                return 0;
            }
            if (memo[i][j] != -1) {
                return memo[i][j];
            }
            int res = 0;
            for (int k = i; k < s.length; ++k) {
                if (s[k] == t[j]) {
                    res += helper(s, k+1, t, j+1, memo);
                }
            }
            memo[i][j] = res;
            return res;
        }
    }

    /**
     * Solution 3: Dynamic programming
     *
     * Same idea as Solution 2 except that it is bottom-up DP. dp[i][j] = number of valid sequences for t.substring(i)
     * and s.substring(j). We iterate from last to first, carry forward result and accumulate if we see a matching
     * character: dp[i][j] = dp[i][j+1] + (dp[i+1][j+1] if characters match at current positions of s and t)
     *
     * One nice trick is to allocation one more row and column and define boundary condition, to avoid index testing.
     */
    class Solution3 {
        public int numDistinct(String s, String t) {
            char[] s_chars = s.toCharArray();
            char[] t_chars = t.toCharArray();
            int[][] memo = new int[t_chars.length+1][s_chars.length+1];
            // boundary condition
            for (int i = 0; i <= s_chars.length; ++i) {
                memo[t_chars.length][i] = 1;
            }
            for (int i = t_chars.length-1; i >= 0 ; --i) {
                for (int j = s_chars.length-1; j >= 0; --j) {
                    memo[i][j] = memo[i][j+1];
                    if (s_chars[j] == t_chars[i]) {
                        memo[i][j] += memo[i+1][j+1];
                    }
                }
            }
            return memo[0][0];
        }
    }

    /**
     * Solution 4: Dynamic programming, with reduced space usage
     *
     * From Solution 3 it is easily seen that we only need the last row of results.
     */
    class Solution4 {
        public int numDistinct(String s, String t) {
            char[] s_chars = s.toCharArray();
            char[] t_chars = t.toCharArray();
            int[] cur = new int[s_chars.length+1];
            int[] last = new int[s_chars.length+1];
            // boundary condition
            for (int i = 0; i <= s_chars.length; ++i) {
                last[i] = 1;
            }
            for (int i = t_chars.length-1; i >= 0 ; --i) {
                cur[s_chars.length] = 0;
                for (int j = s_chars.length-1; j >= 0; --j) {
                    cur[j] = cur[j+1];
                    if (s_chars[j] == t_chars[i]) {
                        cur[j] += last[j+1];
                    }
                }
                int[] tmp = last;
                last = cur;
                cur = tmp;
            }
            // note that cur and last are already swapped
            return last[0];
        }
    }
}
