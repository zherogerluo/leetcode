/**
 * LeetCode #925, easy
 *
 * Your friend is typing his name into a keyboard. Sometimes, when typing a character c, the key might get long pressed,
 * and the character will be typed 1 or more times.
 *
 * You examine the typed characters of the keyboard. Return True if it is possible that it was your friends name, with
 * some characters (possibly none) being long pressed.
 *
 * Example 1:
 *
 * Input: name = "alex", typed = "aaleex"
 * Output: true
 * Explanation: 'a' and 'e' in 'alex' were long pressed.
 *
 * Example 2:
 *
 * Input: name = "saeed", typed = "ssaaedd"
 * Output: false
 * Explanation: 'e' must have been pressed twice, but it wasn't in the typed output.
 *
 * Example 3:
 *
 * Input: name = "leelee", typed = "lleeelee"
 * Output: true
 *
 * Example 4:
 *
 * Input: name = "laiden", typed = "laiden"
 * Output: true
 * Explanation: It's not necessary to long press any character.
 *
 * Constraints:
 *
 * 1 <= name.length <= 1000
 * 1 <= typed.length <= 1000
 * name and typed contain only lowercase English letters.
 */

package String;

public class LongPressedName {
    /**
     * Solution 1: Two pointers
     *
     * Aggressively matching characters one by one, if no match, try to consume chars that are the same as last char
     * (long pressed ones). Finally the two pointers should both reach the end.
     */
    class Solution1 {
        public boolean isLongPressedName(String name, String typed) {
            char last = ' ';
            char[] cName = name.toCharArray(), cTyped = typed.toCharArray();
            int i = 0, j = 0;
            while (i < cName.length && j < cTyped.length) {
                if (cName[i] != cTyped[j]) {
                    return false;
                }
                last = cName[i];
                ++i;
                ++j;
                if (i < cName.length && j < cTyped.length && cName[i] == cTyped[j]) {
                    continue;
                }
                // consume long pressed ones only when no match is found, or reached name string's end
                while (j < cTyped.length && cTyped[j] == last) {
                    // don't put j++ in the termination condition in general, it is error prone
                    ++j;
                }
            }
            return i == cName.length && j == cTyped.length;
        }
    }
}
