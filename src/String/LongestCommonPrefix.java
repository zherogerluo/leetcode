/**
 * LeetCode #14, easy
 *
 * Write a function to find the longest common prefix string amongst an array of strings.

 If there is no common prefix, return an empty string "".

 Example 1:

 Input: ["flower","flow","flight"]
 Output: "fl"
 Example 2:

 Input: ["dog","racecar","car"]
 Output: ""
 Explanation: There is no common prefix among the input strings.
 Note:

 All given inputs are in lowercase letters a-z.
 */

package String;

public class LongestCommonPrefix {
    /**
     * Solution 1:
     *
     * Look at each char one by one, and for each char, examine all strings at that char's location, if any mismatch
     * is found, then the result is just the substring extending from zeroth position to that char (exclusive).
     *
     * Tricky part: Check for out of bound index.
     *
     * Time complexity: O(nk) where k is the length of shortest string. Space complexity: O(1)
     */
    class Solution1 {
        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0 || strs[0].length() == 0) return "";
            int i = 0;
            for (; i < strs[0].length(); i++) {
                char c = strs[0].charAt(i);
                for (String s : strs) {
                    if (i >= s.length() || s.charAt(i) != c) { // check for out of bound index
                        return strs[0].substring(0, i);
                    }
                }
            }
            return strs[0];
        }
    }

    /**
     * Solution 2:
     *
     * Let's assume the first string is the result. Cut the last character of the result until all strings has this
     * result as prefix.
     *
     * Note: Comparing with Solution 1, which has good performance when the longest common prefix is very short, this
     * solution has good performance when the longest common prefix is very long, close to the length of shortest
     * string.
     *
     * Time complexity: O(n * k^2) where k is the length of shortest string, since function startsWith() compares k
     * characters.
     *
     * Space complexity: O(k), for the initial storage of res.
     */
    class Solution2 {
        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0) return "";
            String res = strs[0];
            for (int i = 1; i < strs.length; i++) {
                while (!strs[i].startsWith(res)) res = res.substring(0, res.length()-1);
            }
            return res;
        }
    }
}
