/**
 * LeetCode #485, easy
 *
 * Given a binary array, find the maximum number of consecutive 1s in this array.
 *
 * Example 1:
 * Input: [1,1,0,1,1,1]
 * Output: 3
 * Explanation: The first two digits or the last three digits are consecutive 1s.
 *     The maximum number of consecutive 1s is 3.
 * Note:
 *
 * The input array will only contain 0 and 1.
 * The length of input array is a positive integer and will not exceed 10,000
 */

package Array;

public class MaxConsecutiveOnes {
    /**
     * Solution 1:
     *
     * Trivial but need to collect dangling count in the end!
     */
    class Solution1 {
        public int findMaxConsecutiveOnes(int[] nums) {
            int res = 0, count = 0;
            for (int num : nums) {
                if (num == 1) count++;
                else {
                    res = Math.max(res, count);
                    count = 0;
                }
            }
            return Math.max(res, count); // don't forget this!
        }
    }

    /**
     * Solution 2:
     *
     * Easier solution to write bug-free.
     */
    class Solution2 {
        public int findMaxConsecutiveOnes(int[] nums) {
            int res = 0, count = 0;
            for (int num : nums) {
                if (num == 1) count++;
                else count = 0;
                res = Math.max(res, count);
            }
            return res;
        }
    }
}
