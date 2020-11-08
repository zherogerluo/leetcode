/**
 * LeetCode #647, medium
 *
 * Given a string, your task is to count how many palindromic substrings in this string.
 *
 * The substrings with different start indexes or end indexes are counted as different substrings even they consist of
 * same characters.
 *
 * Example 1:
 * Input: "abc"
 * Output: 3
 * Explanation: Three palindromic strings: "a", "b", "c".
 *
 * Example 2:
 * Input: "aaa"
 * Output: 6
 * Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
 *
 * Note:
 * The input string length won't exceed 1000.
 */

public class PalindromicSubstrings {
    /**
     * Solution 1:
     *
     * Simply expand until not palindromic and count. Trick is to use a helper index k for the ease of accounting for
     * palindromes of both odd and even number of characters.
     *
     * Time complexity: O(n^2). Space complexity: O(1).
     */
    class Solution1 {
        public int countSubstrings(String s) {
            int result = 0;
            char[] chars = s.toCharArray();
            for (int k = 0; k < 2 * chars.length - 1; ++k) {
                int i = k / 2, j = (k + 1) / 2;
                while (i >= 0 && j < chars.length && chars[i] == chars[j]) {
                    ++result;
                    --i;
                    ++j;
                }
            }
            return result;
        }
    }
}