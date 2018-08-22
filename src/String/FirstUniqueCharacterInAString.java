/**
 * LeetCode #387, easy
 *
 * Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1.
 *
 * Examples:
 *
 * s = "leetcode"
 * return 0.
 *
 * s = "loveleetcode",
 * return 2.
 *
 * Note: You may assume the string contain only lowercase letters.
 */

package String;

public class FirstUniqueCharacterInAString {
    /**
     * Solution 1: Hash map (equivalent)
     *
     * Use a hash map (or a count array since the input is limited to lowercase chars) to record the number of
     * appearances for every char, and return the first one whose count is 1. Note we don't want to return count == 0
     * ones.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution1 {
        public int firstUniqChar(String s) {
            int[] count = new int[26];
            char[] cs = s.toCharArray();
            for (char c : cs) count[c-'a'] += 1;
            for (int i = 0; i < cs.length; i++) {
                if (count[cs[i]-'a'] == 1) return i;
            }
            return -1;
        }
    }
}
