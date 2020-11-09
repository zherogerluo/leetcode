/**
 * LeetCode #394, medium
 *
 * Given an encoded string, return its decoded string.
 *
 * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated
 * exactly k times. Note that k is guaranteed to be a positive integer.
 *
 * You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.
 *
 * Furthermore, you may assume that the original data does not contain any digits and that digits are only for those
 * repeat numbers, k. For example, there won't be input like 3a or 2[4].
 *
 * Example 1:
 * Input: s = "3[a]2[bc]"
 * Output: "aaabcbc"
 *
 * Example 2:
 * Input: s = "3[a2[c]]"
 * Output: "accaccacc"
 *
 * Example 3:
 * Input: s = "2[abc]3[cd]ef"
 * Output: "abcabccdcdcdef"
 *
 * Example 4:
 * Input: s = "abc3[cd]xyz"
 * Output: "abccdcdcdxyz"
 */

public class DecodeString {
    /**
     * Solution 1: Depth-first search
     *
     * Recursively build string whenever we see a '['. Note we need to do an additional increment of index for '['.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution1 {
        public String decodeString(String s) {
            return decode(s.toCharArray(), new int[]{0}).toString();
        }

        private StringBuilder decode(char[] c, int[] i) {
            int count = 0;
            StringBuilder s = new StringBuilder();
            while (i[0] < c.length && c[i[0]] != ']') {
                if (c[i[0]] >= 'a' && c[i[0]] <= 'z' || c[i[0]] >= 'A' && c[i[0]] <= 'Z' ) {
                    s.append(c[i[0]]);
                } else if (c[i[0]] >= '0' && c[i[0]] <= '9') {
                    count = count * 10 + c[i[0]] - '0';
                } else if (c[i[0]] == '[') {
                    ++i[0];
                    StringBuilder sub = decode(c, i);
                    for (int j = 0; j < count; ++j) {
                        s.append(sub);
                    }
                    count = 0;
                }
                ++i[0];
            }
            return s;
        }
    }
}
