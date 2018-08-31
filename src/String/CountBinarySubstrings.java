/**
 * LeetCode #696, easy
 *
 * Give a string s, count the number of non-empty (contiguous) substrings that have the same number of 0's and 1's,
 * and all the 0's and all the 1's in these substrings are grouped consecutively.
 *
 * Substrings that occur multiple times are counted the number of times they occur.
 *
 * Example 1:
 * Input: "00110011"
 * Output: 6
 * Explanation: There are 6 substrings that have equal number of consecutive 1's and 0's: "0011", "01", "1100", "10",
 * "0011", and "01".
 *
 * Notice that some of these substrings repeat and are counted the number of times they occur.
 * Also, "00110011" is not a valid substring because all the 0's (and 1's) are not grouped together.
 *
 * Example 2:
 * Input: "10101"
 * Output: 4
 * Explanation: There are 4 substrings: "10", "01", "10", "01" that have equal number of consecutive 1's and 0's.
 *
 * Note:
 * s.length will be between 1 and 50,000.
 * s will only consist of "0" or "1" characters.
 */

package String;

public class CountBinarySubstrings {
    /**
     * Solution 1:
     *
     * Count last seen char and currently seen char, they are 00..11 or 11..00 sequences. Then the number of valid
     * substrings has to be the minimum of the two counts. For example, 0001111, counts are 3 and 4, and the valid
     * substrings are 000111, 0011 and 01. It is basically a greedy algorithm that gets the longest valid substring
     * every time, and reduce to shorter valid substring by trimming the leading and trailing chars.
     *
     * Tricky part is to collect the dangling counts in the end, and properly initialize current count when
     * encountering a new char (initial count should be 1, not 0).
     */
    class Solution1 {
        public int countBinarySubstrings(String s) {
            int lastCount = 0, curCount = 0, res = 0; // the initial value of curCount shall be 0 here
            char curChar = s.charAt(0);
            for (char c: s.toCharArray()) {
                if (c == curChar) curCount++;
                else {
                    res += Math.min(lastCount, curCount);
                    curChar = c;
                    lastCount = curCount;
                    curCount = 1; // initial value is not zero
                }
            }
            return res + Math.min(lastCount, curCount); // need to collect dangling count
        }
    }
}
