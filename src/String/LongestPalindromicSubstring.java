/**
 * LeetCode #5, medium
 *
 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

 Example 1:

 Input: "babad"
 Output: "bab"
 Note: "aba" is also a valid answer.
 Example 2:

 Input: "cbbd"
 Output: "bb"
 */

package String;

public class LongestPalindromicSubstring {
    /**
     * Solution 1: Traverse and expand
     *
     * For every character in the string, expand it to left and right if palindromic criteria are met, i.e.
     * the left and right neighbor is the same character.
     *
     * Tricky part: The pivot (center of palindrome) can be a specific char (like "aba"), or between two chars
     * (like "abba"). These two conditions should not be mutually exclusive.
     *
     * Time complexity: O(n^2). Space complexity: O(1)
     */
    class Solution1 {
        public String longestPalindrome(String s) {
            String res = "";
            for (int i = 0; i < s.length(); i++) {
                String s1 = expand(s, i, i), s2 = expand(s, i, i+1);
                if (s1.length() > res.length()) res = s1;
                if (s2.length() > res.length()) res = s2;
            }
            return res;
        }

        private String expand(String s, int i, int j) {
            while (i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
                i--; j++;
            }
            return s.substring(i+1, j);
        }
    }

    /**
     * Solution 2: Slightly optimized from Solution 1, using an int array to record substring bounds rather than
     * returning a palindromic substring every time which is expensive. Note that int arrays are passed in as
     * reference, not copies.
     */
    class Solution2 {
        public String longestPalindrome(String s) {
            int[] bounds = {0, 1};
            for (int i = 0; i < s.length(); i++) {
                expand(s, i, i, bounds);
                expand(s, i, i+1, bounds);
            }
            return s.substring(bounds[0], bounds[1]);
        }

        private void expand(String s, int i, int j, int[] bounds) {
            while (i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
                i--; j++;
            }
            if (j-i-1 > bounds[1]-bounds[0]) {
                bounds[0] = i+1; bounds[1] = j;
            }
        }
    }

    /**
     * Solution 3: Manacher's Algorithm
     *
     * Linear time solution, basic idea is to reuse the found palindrome lengths and take advantage of the symmetry
     * property. Dynamic programming, O(n) space.
     *
     * Reference: https://articles.leetcode.com/longest-palindromic-substring-part-ii/
     *
     * Time complexity: O(n). Space complexity: O(n)
     */
    class Solution3 {
        // TODO
    }
}
