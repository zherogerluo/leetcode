/**
 * LeetCode #20, easy
 *
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

 An input string is valid if:

 Open brackets must be closed by the same type of brackets.
 Open brackets must be closed in the correct order.
 Note that an empty string is also considered valid.

 Example 1:

 Input: "()"
 Output: true
 Example 2:

 Input: "()[]{}"
 Output: true
 Example 3:

 Input: "(]"
 Output: false
 Example 4:

 Input: "([)]"
 Output: false
 Example 5:

 Input: "{[]}"
 Output: true
 */

package String;

import java.util.*;

public class ValidParentheses {
    /**
     * Solution 1: Stack
     *
     * Use a stack to remember all the left parentheses. Once other character pops up, take one from stack top and
     * check if it matches the current character. Return true only when reaching end AND the stack is empty.
     *
     * Time complexity: O(n). Space complexity: O(n)
     */
    class Solution1 {
        public boolean isValid(String s) {
            if (s == null) return false;
            Deque<Character> stack = new ArrayDeque<>();
            for (char c : s.toCharArray()) {
                if (c == '(' || c == '[' || c == '{') stack.push(c);
                else if (!stack.isEmpty()) {
                    char left = stack.pop();
                    if (left == '(' && c != ')') return false;
                    if (left == '[' && c != ']') return false;
                    if (left == '{' && c != '}') return false;
                }
                else return false;
            }
            return stack.isEmpty();
        }
    }
}
