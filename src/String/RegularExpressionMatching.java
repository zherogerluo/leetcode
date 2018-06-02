/**
 * LeetCode #10, hard
 *
 * Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.

 '.' Matches any single character.
 '*' Matches zero or more of the preceding element.
 The matching should cover the entire input string (not partial).

 Note:

 s could be empty and contains only lowercase letters a-z.
 p could be empty and contains only lowercase letters a-z, and characters like . or *.
 Example 1:

 Input:
 s = "aa"
 p = "a"
 Output: false
 Explanation: "a" does not match the entire string "aa".
 Example 2:

 Input:
 s = "aa"
 p = "a*"
 Output: true
 Explanation: '*' means zero or more of the precedeng element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
 Example 3:

 Input:
 s = "ab"
 p = ".*"
 Output: true
 Explanation: ".*" means "zero or more (*) of any character (.)".
 Example 4:

 Input:
 s = "aab"
 p = "c*a*b"
 Output: true
 Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore it matches "aab".
 Example 5:

 Input:
 s = "mississippi"
 p = "mis*is*p*."
 Output: false
 */

package String;

public class RegularExpressionMatching {
    /**
     * Solution 1: Recursion
     *
     * Recursive algorithm, proceed step by step to reduce the problem to smaller scale.
     *
     * Use two pointers i and j to track current positions in s and p.
     *
     * Edge cases: i and j are both out of bounds (match), or j is out of bound (no match). Note i out of bound alone
     * does not falsify the case because the remaining of p could be something like ".*" and it is still a valid match.
     *
     * If the character matches, i.e. both i and j are in bound, and either s[i] == p[j] or p[j] == '.', then test
     * the next character in p. If it is '*', then we can consume one character in s, or jump past this '*' (does not
     * consume).
     *
     * If the character does not match, it could be either '*' (should only be at the begining of p though), which
     * results in a no-match; Or it could be a valid a-z character, then we again test the next in p, if it is '*' we
     * can skip it, otherwise it is a no-match.
     *
     * Tricky part: test if i and j are in bound!
     *
     * Time complexity: O(2^n) because of two branches in each recursive step. However like Fibonacci sequence,
     * the tight upper bound may not be O(2^n), could be something like O(1.6^n).
     *
     * Space complexity: O(n)
     */
    class Solution1 {
        public boolean isMatch(String s, String p) {
            if (s.length() == 0 && p.length() == 0) return true;
            return isMatch(s, p, 0, 0);
        }

        private boolean isMatch(String s, String p, int i, int j) {
            if (s.length() == i && p.length() == j) return true;
            if (p.length() == j) return false;
            boolean res = false;
            if (i < s.length() && (p.charAt(j) == '.' || s.charAt(i) == p.charAt(j))) {
                // character matches
                if (j < p.length()-1 && p.charAt(j+1) == '*') {
                    res = res || isMatch(s, p, i, j+2) || isMatch(s, p, i+1, j); // this is like Fibonacci problem
                } else res = res || isMatch(s, p, i+1, j+1);
            } else {
                // character does not match
                if (p.charAt(j) == '*') return false;
                if (j < p.length()-1 && p.charAt(j+1) == '*') {
                    res = res || isMatch(s, p, i, j+2);
                }
            }
            return res;
        }
    }

    /**
     * Solution 2: Recursive
     *
     * Add memoization to Solution1.
     *
     * Time complexity: O(mn) because all results are in memo which are all calculated only once. The memo matrix may
     * look sparse?
     *
     * Space complexity: O(mn)
     */
    class Solution2 {
        public boolean isMatch(String s, String p) {
            if (s.length() == 0 && p.length() == 0) return true;
            return isMatch(s, p, 0, 0, new int[s.length()+1][p.length()+1]); // pay attention to the +1 part
        }

        private boolean isMatch(String s, String p, int i, int j, int[][] memo) {
            if (s.length() == i && p.length() == j) return true;
            if (p.length() == j) return false;
            if (memo[i][j] != 0) return memo[i][j] == 1;
            boolean res = false;
            if (i < s.length() && (p.charAt(j) == '.' || s.charAt(i) == p.charAt(j))) {
                // character matches
                if (j < p.length()-1 && p.charAt(j+1) == '*') {
                    res = res || isMatch(s, p, i, j+2, memo) || isMatch(s, p, i+1, j, memo);
                } else res = res || isMatch(s, p, i+1, j+1, memo);
            } else {
                // character does not match
                if (p.charAt(j) == '*') return false;
                if (j < p.length()-1 && p.charAt(j+1) == '*') {
                    res = res || isMatch(s, p, i, j+2, memo);
                }
            }
            memo[i][j] = res ? 1 : -1;
            return res;
        }
    }

    /**
     * Solution 3: Dynamic programming
     *
     * Very much like the recursive solution, except that it is building the memo array explicitly.
     *
     * Tricky part #1: Remember to initializing the first row (advancing in p for sequences like "a*b*c*")
     *
     * Tricky part #2: Figure out which previous DP result to use
     *
     * Time complexity: O(mn). Space complexity: O(mn)
     */
    class Solution3 {
        public boolean isMatch(String s, String p) {
            boolean[][] res = new boolean[s.length()+1][p.length()+1];
            res[0][0] = true;
            for (int j = 1; j < p.length(); j++) {
                if (p.charAt(j) == '*' && res[0][j-1]) res[0][j+1] = true;
            }
            for (int i = 0; i < s.length(); i++) {
                for (int j = 0; j < p.length(); j++) {
                    if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') {
                        res[i+1][j+1] = res[i][j];
                    } else if (j > 0 && p.charAt(j) == '*') {
                        if (s.charAt(i) == p.charAt(j-1) || p.charAt(j-1) == '.') {
                            res[i+1][j+1] = res[i][j+1] || res[i+1][j-1];
                            // the above line is tricky, especially the res[i][j+1] not being res[i][j-1]
                            // and we don't need a res[i+1][j] term
                        } else {
                            res[i+1][j+1] = res[i+1][j-1];
                        }
                    }
                }
            }
            return res[s.length()][p.length()];
        }
    }
}
