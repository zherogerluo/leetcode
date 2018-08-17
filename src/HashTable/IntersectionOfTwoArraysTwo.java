/**
 * LeetCode #350, easy
 *
 * Given two arrays, write a function to compute their intersection.
 *
 * Example 1:
 *
 * Input: nums1 = [1,2,2,1], nums2 = [2,2]
 * Output: [2,2]
 *
 * Example 2:
 *
 * Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * Output: [4,9]
 *
 * Note:
 *
 * Each element in the result should appear as many times as it shows in both arrays.
 * The result can be in any order.
 *
 * Follow up:
 *
 * What if the given array is already sorted? How would you optimize your algorithm?
 * What if nums1's size is small compared to nums2's size? Which algorithm is better?
 * What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements
 * into the memory at once?
 */

package HashTable;

import java.util.*;

public class IntersectionOfTwoArraysTwo {
    /**
     * Solution 1: Hash map
     *
     * Use hash map to count frequencies of one array, loop through the other array and check existence in map, if
     * present, add it to result and decrement count. One trick is to store shorter array to map and stop if map is
     * empty, in order to reduce map operations.
     *
     * Time complexity: O(m + n). Space complexity: O(min(m, n)).
     */
    class Solution1 {
        public int[] intersect(int[] nums1, int[] nums2) {
            if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) return new int[0];
            if (nums1.length > nums2.length) return intersect(nums2, nums1);
            // nums1 is shorter
            Map<Integer, Integer> map = new HashMap<>();
            for (int num : nums1) map.put(num, map.getOrDefault(num, 0) + 1);
            List<Integer> list = new ArrayList<>();
            for (int num : nums2) {
                if (map.containsKey(num)) {
                    list.add(num);
                    int count = map.get(num) - 1;
                    if (count == 0) map.remove(num);
                    else map.put(num, count);
                }
                if (map.isEmpty()) break;
            }
            int[] res = new int[list.size()];
            int i = 0;
            for (int num : list) res[i++] = num;
            return res;
        }
    }

    /**
     * Solution 2: Sorting with two pointers
     *
     * Sort both arrays, and use two pointers, increment smaller one until two pointers see the same element, which
     * is the case where we add the element to the result and increment both pointers.
     *
     * Time complexity: O(max(m * log(m), n * log(n), m + n).
     */
    class Solution2 {
        public int[] intersect(int[] nums1, int[] nums2) {
            Arrays.sort(nums1);
            Arrays.sort(nums2);
            int i = 0, j = 0, k = 0;
            int[] res = new int[Math.max(nums1.length, nums2.length)];
            while (i < nums1.length && j < nums2.length) {
                if (nums1[i] < nums2[j]) i++;
                else if (nums1[i] > nums2[j]) j++;
                else {
                    res[k++] = nums1[i];
                    i++; j++;
                }
            }
            return Arrays.copyOf(res, k);
        }
    }
}
