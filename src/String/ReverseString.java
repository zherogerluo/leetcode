/**
 * LeetCode #344, easy
 *
 * Write a function that takes a string as input and returns the string reversed.
 *
 * Example 1:
 *
 * Input: "hello"
 * Output: "olleh"
 *
 * Example 2:
 *
 * Input: "A man, a plan, a canal: Panama"
 * Output: "amanaP :lanac a ,nalp a ,nam A"
 */

package String;

public class ReverseString {
    /**
     * Solution 1: Two pointers
     *
     * Trivial.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution1 {
        public String reverseString(String s) {
            char[] chars = new char[s.length()];
            int i = chars.length-1;
            for (char c : s.toCharArray()) {
                chars[i--] = c;
            }
            return new String(chars);
        }
    }
}
