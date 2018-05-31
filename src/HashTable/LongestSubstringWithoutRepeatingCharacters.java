/**
 * LeetCode #3, medium
 *
 * Given a string, find the length of the longest substring without repeating characters.

 Examples:

 Given "abcabcbb", the answer is "abc", which the length is 3.

 Given "bbbbb", the answer is "b", with the length of 1.

 Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 */

package HashTable;

import java.util.*;

public class LongestSubstringWithoutRepeatingCharacters {
    /**
     * Solution 1: Hash map
     *
     * Traverse string and use a hash map to record visited character and its last index, AND a pointer to record
     * starting index of current substring. If current character is visited, AND its last appearance is in this
     * substring, update the starting index. Within the loop, update the hash map, and update the result based on
     * the starting index of current substring.
     *
     * Tricky part: Need to remember the starting point of current substring, need to check if a visited character
     * is within this substring (start is max of start and index+1 checked out from hash map).
     *
     * Other thoughts: Use array to replace hash map for better performance, if the character set is know
     * (ASCII for example, use int[256])
     *
     * Time complexity: O(n). Space complexity: O(n)
     */
    class Solution1 {
        public int lengthOfLongestSubstring(String s) {
            Map<Character, Integer> map = new HashMap<>();
            int res = 0, start = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (map.containsKey(c)) start = Math.max(start, map.get(c) + 1); // tricky part
                res = Math.max(res, i - start + 1);
                map.put(c, i);
            }
            return res;
        }
    }

    /**
     * Solution 2: Hash map
     *
     * Slightly modified from Solution 1: If a character was already visited, remove everything before it (inclusive).
     * Performance hit because of more hash map operations.
     */
    class Solution2 {
        public int lengthOfLongestSubstring(String s) {
            Map<Character, Integer> map = new HashMap<>();
            int res = 0, start = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (map.containsKey(c)) {
                    res = Math.max(res, i - start);
                    int k = map.get(c);
                    while (start <= k) map.remove(s.charAt(start++));
                }
                map.put(c, i);
            }
            return Math.max(res, s.length() - start);
        }
    }
}
