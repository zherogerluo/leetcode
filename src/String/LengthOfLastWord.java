/**
 * LeetCode #58, easy
 *
 * Given a string s consists of upper/lower-case alphabets and empty space characters ' ', return the length of last word in the string.
 *
 * If the last word does not exist, return 0.
 *
 * Note: A word is defined as a character sequence consists of non-space characters only.
 *
 * Example:
 *
 * Input: "Hello World"
 * Output: 5
 */

package String;

public class LengthOfLastWord {
    /**
     * Solution 1:
     *
     * Use Java String methods. Trivial.
     */
    class Solution1 {
        public int lengthOfLastWord(String s) {
            s = s.trim();
            return s.length() - s.lastIndexOf(' ') - 1;
        }
    }
}
