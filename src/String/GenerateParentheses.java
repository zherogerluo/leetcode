/**
 * LeetCode #22, medium
 *
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

 For example, given n = 3, a solution set is:

 [
 "((()))",
 "(()())",
 "(())()",
 "()(())",
 "()()()"
 ]
 */

package String;

import java.util.*;

public class GenerateParentheses {
    /**
     * Solution 1: Insertion
     *
     * Solution of n can be obtained by inserting a pair of parentheses into solution of n-1. Duplicates can be
     * removed by using a set to store results. Bad performance because of duplicates. The algorithm is actually
     * factorial.
     *
     * Time complexity: O(n!). Space complexity: O(4^n) (not asymptotically tight)
     */
    class Solution1 {
        public List<String> generateParenthesis(int n) {
            Queue<String> queue = new LinkedList<>();
            queue.offer("");
            for (int i = 0; i < n; i++) {
                int m = queue.size();
                for (int j = 0; j < m; j++) {
                    String s = queue.poll();
                    int p = s.length();
                    for (int k = 0; k <= p; k++) {
                        // insert a new pair at position k
                        String s1 = s.substring(0, k) + "()" + s.substring(k, p);
                        if (!queue.contains(s1)) queue.offer(s1);
                    }
                }
            }
            return (List<String>) queue;
        }
    }

    /**
     * Solution 2: Backtracking
     *
     * Recursively add '(' or ')' depending on how many '('s have been added. If in the end the number of '(' and ')'
     * matches, then add it to result.
     *
     * Note: Use a char array instead of string concatenation to improve performance. No need to undo the change to
     * the char array because in the next recursion it will be overwritten anyway.
     *
     * Time complexity: O(4^n). Space complexity: O(4^n)
     */
    class Solution2 {
        public List<String> generateParenthesis(int n) {
            List<String> res = new ArrayList<>();
            char[] cs = new char[n*2];
            helper(res, cs, 0, 0);
            return res;
        }

        private void helper(List<String> res, char[] cs, int left, int i) {
            if (left > cs.length/2) return; // too many left parentheses
            if (i == cs.length) { // reach end
                if (left == 0) res.add(new String(cs)); // perfectly balanced, add to result
                return;
            }
            cs[i] = '(';
            helper(res, cs, left+1, i+1);
            if (left > 0) { // add ')' only if we have '(' left unmatched
                cs[i] = ')';
                helper(res, cs, left-1, i+1);
            }
        }
    }

    /**
     * Solution 3: Dynamic programming
     */
    // TODO
}
