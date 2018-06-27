/**
 * LeetCode #76, hard
 *
 * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in
 * complexity O(n).

 Example:

 Input: S = "ADOBECODEBANC", T = "ABC"
 Output: "BANC"
 Note:

 If there is no such window in S that covers all characters in T, return the empty string "".
 If there is such window, you are guaranteed that there will always be only one unique minimum window in S.
 */

package HashTable;

import java.util.*;

public class MinimumWindowSubstring {
    /**
     * Solution 1: Hash map with two pointers
     *
     * Use a hash map to record number of characters needed in the substring. We put a start pointer to record the
     * starting index of current substring being looked at. When iterating char by char, see if the char is what we
     * need, if so, we reduce the debt. We also need to advance the start pointer whenever possible: If char[start]
     * has a negative count, meaning that we already have more than sufficient of this char in current substring,
     * then we can advance the start pointer. Meanwhile we keep an eye on the number of chars remaining, if zero, we
     * record the result.
     *
     * Tricky part is the usage of hash map: It needs to record the appearances need for specific char (debt), and this
     * number can be smaller than zero (we payed off the debt). A side note is that we can use int[256] to improve
     * performance if char is ASCII.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution1 {
        public String minWindow(String s, String t) {
            Map<Character, Integer> map = new HashMap<>();
            for (char c : t.toCharArray()) map.put(c, map.getOrDefault(c, 0) + 1);
            int start = 0, remaining = t.length();
            char[] cs = s.toCharArray();
            String res = "";
            for (int i = 0; i < cs.length; i++) {
                if (map.containsKey(cs[i])) {
                    int debt = map.get(cs[i]) - 1;
                    if (debt >= 0) remaining--;
                    map.put(cs[i], debt);
                }
                while (start <= i) {
                    if (map.containsKey(cs[start])) {
                        int count = map.get(cs[start]);
                        if (count >= 0) break;
                        else map.put(cs[start], count + 1);
                    }
                    start++;
                }
                if (remaining == 0) {
                    if (res.length() == 0 || res.length() > i-start+1) res = s.substring(start, i+1);
                }
            }
            return res;
        }
    }
}
