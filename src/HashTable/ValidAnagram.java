/**
 * LeetCode #242, easy
 *
 * Given two strings s and t , write a function to determine if t is an anagram of s.

 Example 1:

 Input: s = "anagram", t = "nagaram"
 Output: true
 Example 2:

 Input: s = "rat", t = "car"
 Output: false
 Note:
 You may assume the string contains only lowercase alphabets.

 Follow up:
 What if the inputs contain unicode characters? How would you adapt your solution to such case?
 */

package HashTable;

import java.util.*;

public class ValidAnagram {
    /**
     * Solution 1: Sorting
     *
     * Compare sorted strings.
     *
     * Time complexity: O(n * log(n)). Space complexity: O(n).
     */
    class Solution1 {
        public boolean isAnagram(String s, String t) {
            char[] cs1 = s.toCharArray();
            char[] cs2 = t.toCharArray();
            Arrays.sort(cs1);
            Arrays.sort(cs2);
            return new String(cs1).equals(new String(cs2));
        }
    }

    /**
     * Solution 2: Hash map
     *
     * Use a map to record count of characters. Build map for s, and decrement count for t. Once we see a negative
     * index or non-existent char, return false.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        public boolean isAnagram(String s, String t) {
            if (s.length() != t.length()) return false; // need to check length first
            Map<Character, Integer> count = new HashMap<>();
            for (char c : s.toCharArray()) {
                count.put(c, count.getOrDefault(c, 0) + 1);
            }
            for (char c : t.toCharArray()) {
                if (count.getOrDefault(c, 0) == 0) return false;
                count.put(c, count.getOrDefault(c, 0) - 1);
            }
            return true;
        }
    }
}
