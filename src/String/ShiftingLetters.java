/**
 * LeetCode #848, medium
 *
 * We have a string S of lowercase letters, and an integer array shifts.
 *
 * Call the shift of a letter, the next letter in the alphabet, (wrapping around so that 'z' becomes 'a').
 *
 * For example, shift('a') = 'b', shift('t') = 'u', and shift('z') = 'a'.
 *
 * Now for each shifts[i] = x, we want to shift the first i+1 letters of S, x times.
 *
 * Return the final string after all such shifts to S are applied.
 *
 * Example 1:
 *
 * Input: S = "abc", shifts = [3,5,9]
 * Output: "rpl"
 * Explanation:
 * We start with "abc".
 * After shifting the first 1 letters of S by 3, we have "dbc".
 * After shifting the first 2 letters of S by 5, we have "igc".
 * After shifting the first 3 letters of S by 9, we have "rpl", the answer.
 *
 * Note:
 *
 * 1 <= S.length = shifts.length <= 20000
 * 0 <= shifts[i] <= 10 ^ 9
 */

package String;

public class ShiftingLetters {
    /**
     * Solution 1:
     *
     * This is an easy problem. Just accumulate the shifts from end to beginning then do the shifts. The tricky part is
     * to avoid overflow - need to take mod 26 early.
     */
    class Solution1 {
        public String shiftingLetters(String S, int[] shifts) {
            for (int i = shifts.length-1; i >= 0; --i) {
                shifts[i] %= 26;
                if (i < shifts.length-1) {
                    shifts[i] = (shifts[i] + shifts[i+1]) % 26;
                }
            }
            char[] chars = S.toCharArray();
            for (int i = 0; i < chars.length; ++i) {
                chars[i] += shifts[i];
                if (chars[i] > 'z') {
                    chars[i] -= 26;
                }
            }
            return new String(chars);
        }
    }
}
