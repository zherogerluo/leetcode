/**
 * LeetCode #32, hard
 *
 * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.

 Example 1:

 Input: "(()"
 Output: 2
 Explanation: The longest valid parentheses substring is "()"
 Example 2:

 Input: ")()())"
 Output: 4
 Explanation: The longest valid parentheses substring is "()()"
 */

package String;

import java.util.*;

public class LongestValidParentheses {
    /**
     * Solution 1: Stack
     *
     * Use a stack to keep track of visited and unmatched char index. If ')' comes up, and the stack top is '(', then
     * we can update the result: the current longest length is i - index of next unmatched.
     *
     * Note: Everything goes into the stack unless there is a match, whether it is '(' or ')'. Thus we need to check
     * the stack top to see what it is when we need to find a match for current ')'.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution1 {
        public int longestValidParentheses(String s) {
            Deque<Integer> stack = new ArrayDeque<>();
            int res = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(') stack.push(i);
                else {
                    if (!stack.isEmpty() && s.charAt(stack.peek()) == '(') { // check the stack top element
                        stack.pop();
                        int start = stack.isEmpty() ? -1 : stack.peek();
                        res = Math.max(res, i - start);
                    } else stack.push(i);
                }
            }
            return res;
        }
    }

    /**
     * Solution 2: Stack
     *
     * Very similar to Solution 1, except that we only keep '(' char indexes in the stack, and uses a start pointer
     * to record the last unmatched ')' index. When we update the result, the length is either i-start (last
     * unmatched is ')') or i-index at stack top (last unmatched is '(').
     */
    class Solution2 {
        public int longestValidParentheses(String s) {
            int res = 0, start = -1;
            Deque<Integer> stack = new ArrayDeque<>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '(') stack.push(i);
                else {
                    if (!stack.isEmpty()) {
                        stack.pop();
                        res = Math.max(res, stack.isEmpty() ? i-start : i-stack.peek());
                    } else {
                        start = i;
                    }
                }
            }
            return res;
        }
    }

    /**
     * Solution 3: Dynamic programming
     *
     * dp[i]: longest valid parentheses ending at i
     *
     * To calculate dp[i+1], it is non-zero only when char at i+1 is ')'. Then we look at dp[i], rewind all the way
     * back for dp[i] characters and check the char there, which is the last char unmatched. Let its index be j, if
     * it is a '(', we find a match, then dp[i+1] is simply 2 + dp[i] + dp[j-1]. Otherwise, we don't have a match
     * for i+1, then dp[i+1] is zero.
     *
     * Tricky part: Check for array bounds!
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution3 {
        public int longestValidParentheses(String s) {
            // dp[i]: longest valid parentheses ending at i
            int[] dp = new int[s.length()];
            int res = 0;
            for (int i = 1; i < dp.length; i++) {
                if (s.charAt(i) == ')') {
                    int cur = 0;
                    int j = i - dp[i-1] - 1;
                    if (j >= 0 && s.charAt(j) == '(') {
                        cur = 2 + dp[i-1] + (j-1 >= 0 ? dp[j-1] : 0);
                    }
                    dp[i] = cur;
                    res = Math.max(res, cur);
                }
            }
            return res;
        }
    }
}
