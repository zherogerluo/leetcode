/**
 * LeetCode #448, easy
 *
 * Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
 *
 * Find all the elements of [1, n] inclusive that do not appear in this array.
 *
 * Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra
 * space.
 *
 * Example:
 *
 * Input:
 * [4,3,2,7,8,2,3,1]
 *
 * Output:
 * [5,6]
 */

package Array;

import java.util.*;

public class FindAllNumbersDisappearedInAnArray {
    /**
     * Solution 1: Sorting
     *
     * Sort the array, and check which one is missing. Keep a num variable as target, if less than it, keep going, if
     * larger, then add the target to the result. This solution is not easy to write bug free, and it is O(n * log(n))
     * in time. Not recommended.
     *
     * Time complexity: O(n * log(n)). Space complexity: O(1).
     */
    class Solution1 {
        public List<Integer> findDisappearedNumbers(int[] nums) {
            List<Integer> res = new ArrayList<>();
            Arrays.sort(nums);
            int num = 1, i = 0;
            while (num <= nums.length) {
                if (i >= nums.length || nums[i] > num) res.add(num++);
                else if (nums[i] < num) i++;
                else {
                    i++;
                    num++;
                }
            }
            return res;
        }
    }

    /**
     * Solution 2: Hash set (mark array)
     *
     * Use a hash set or mark array to mark the numbers seen, and output those that are not marked.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        public List<Integer> findDisappearedNumbers(int[] nums) {
            List<Integer> res = new ArrayList<>();
            boolean[] mark = new boolean[nums.length + 1];
            for (int num : nums) mark[num] = true;
            for (int i = 1; i < mark.length; i++) {
                if (!mark[i]) res.add(i);
            }
            return res;
        }
    }

    /**
     * Solution 3:
     *
     * The idea is to put all numbers in the right place. While iterating through the array, if a wrong number is
     * encountered, put it to the right place and repeat the same thing for the number at that place until index and
     * value match. Once we have done this, in the end, the results are just the mismatches.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution3 {
        public List<Integer> findDisappearedNumbers(int[] nums) {
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                int index = nums[i] - 1; // note it is not initialized to nums[0] - 1
                while (index + 1 != nums[index]) {
                    int tmp = nums[index] - 1;
                    nums[index] = index + 1;
                    index = tmp;
                }
            }
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != i + 1) res.add(i + 1);
            }
            return res;
        }
    }

    /**
     * Solution 4:
     *
     * In-place marking, similar to Solution 2 except that we mark the existence by flipping the sign at the right
     * index position.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution {
        public List<Integer> findDisappearedNumbers(int[] nums) {
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                int index = (nums[i] > 0 ? nums[i] : -nums[i]) - 1;
                if (nums[index] > 0) nums[index] = -nums[index];
            }
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] > 0) res.add(i + 1);
            }
            return res;
        }
    }
}
