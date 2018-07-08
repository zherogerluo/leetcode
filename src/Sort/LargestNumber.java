/**
 * LeetCode #179, medium
 *
 * Given a list of non negative integers, arrange them such that they form the largest number.

 Example 1:

 Input: [10,2]
 Output: "210"
 Example 2:

 Input: [3,30,34,5,9]
 Output: "9534330"
 Note: The result may be very large, so you need to return a string instead of an integer.
 */

package Sort;

import java.util.*;

public class LargestNumber {
    /**
     * Solution 1: Sorting
     *
     * Convert all numbers to strings and sort them based on their concatenations. Tricky part is to deal with
     * leading zeros. Fortunately this only happends for "000..." case, so we just return "0".
     */
    class Solution1 {
        public String largestNumber(int[] nums) {
            if (nums == null || nums.length == 0) return "";
            String[] strs = new String[nums.length];
            for (int i = 0; i < nums.length; i++) strs[i] = String.valueOf(nums[i]);
            Arrays.sort(strs, new Comparator<String>(){
                @Override
                public int compare(String a, String b) {
                    return (b + a).compareTo(a + b); // sort based on concatenations, larger one comes first
                }
            });
            StringBuilder sb = new StringBuilder();
            for (String s : strs) sb.append(s);
            if (sb.charAt(0) == '0') return "0"; // deal with leading zero. this would only happend for "000..."
            return sb.toString();
        }
    }
}
