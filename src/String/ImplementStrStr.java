/**
 * LeetCode #28, easy
 *
 * Implement strStr().

 Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.

 Example 1:

 Input: haystack = "hello", needle = "ll"
 Output: 2
 Example 2:

 Input: haystack = "aaaaa", needle = "bba"
 Output: -1
 Clarification:

 What should we return when needle is an empty string? This is a great question to ask during an interview.

 For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().
 */

package String;

public class ImplementStrStr {
    /**
     * Solution 1: Brutal force
     *
     * For every substring of needle's length, check if they are equal.
     *
     * Time complexity: O(mn). Space complexity: O(m)? (substring storage).
     */
    class Solution1 {
        public int strStr(String haystack, String needle) {
            if (haystack == null || needle == null) return -1;
            if (needle.length() == 0) return 0;
            if (haystack.length() < needle.length()) return -1;
            final int n1 = haystack.length(), n2 = needle.length();
            for (int i = 0; i <= n1-n2; i++) { // important optimization
                if (haystack.substring(i, i+n2).equals(needle)) return i;
            }
            return -1;
        }
    }

    /**
     * Solution 2: Brutal force
     *
     * Same as Solution 1 except without using Java String methods.
     */
    class Solution2 {
        public int strStr(String haystack, String needle) {
            if (haystack == null || needle == null) return -1;
            if (needle.length() == 0) return 0;
            if (haystack.length() < needle.length()) return -1;
            char[] cs1 = haystack.toCharArray(), cs2 = needle.toCharArray();
            final int n1 = cs1.length, n2 = cs2.length;
            for (int k = 0; k <= n1-n2; k++) {
                int i = k, j = 0;
                while (i < n1 && j < n2 && cs1[i] == cs2[j]) {
                    i++; j++;
                }
                if (j == n2) return k;
            }
            return -1;
        }
    }

    /**
     * Solution 3: BMH or KMP
     */
    // TODO
}
