/**
 * LeetCode #49, medium
 *
 * Given an array of strings, group anagrams together.

 Example:

 Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
 Output:
 [
 ["ate","eat","tea"],
 ["nat","tan"],
 ["bat"]
 ]
 Note:

 All inputs will be in lowercase.
 The order of your output does not matter.
 */

package HashTable;

import java.util.*;

public class GroupAnagrams {
    /**
     * Solution 1: Hash map
     *
     * All anagrams have same characters, so we can easily sort strings to tell if they are anagrams. Use a hash map
     * to store all strings with the same anagram (sorted, as key), and just return a collection of all values as
     * result.
     *
     * Time complexity: O(n * k * log(k)) where n is number of strings, k is the longest length of all strings.
     * Space complexity: O(n + k).
     *
     * Note: Time complexity can be improved to O(n * k) if using bucket sort.
     */
    class Solution1 {
        public List<List<String>> groupAnagrams(String[] strs) {
            Map<String, List<String>> map = new HashMap<>();
            for (int i = 0; i < strs.length; i++) {
                char[] cs = strs[i].toCharArray();
                Arrays.sort(cs);
                String sorted = new String(cs);
                if (!map.containsKey(sorted)) map.put(sorted, new ArrayList<String>());
                map.get(sorted).add(strs[i]);
            }
            return new ArrayList<>(map.values());
        }
    }
}
