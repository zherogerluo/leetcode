/**
 * LeetCode #560, medium
 *
 * Given an array of integers nums and an integer k, return the total number of continuous subarrays whose sum equals
 * to k.
 *
 * Example 1:
 *
 * Input: nums = [1,1,1], k = 2
 * Output: 2
 *
 * Example 2:
 *
 * Input: nums = [1,2,3], k = 3
 * Output: 2
 *
 * Constraints:
 *
 * 1 <= nums.length <= 2 * 10^4
 * -1000 <= nums[i] <= 1000
 * -10^7 <= k <= 10^7
 */

public class SubarraySumEqualsK {
    /**
     * Solution 1: Brutal force
     *
     * Two pointers scanning from beginning to end and calculate sum along the way, which saves n time than calculating
     * sum every time. The trick part is to increment/decrement res and sum in right order, basically need to make sure
     * sum reflects sum between i and j (inclusive) all the time.
     *
     * Time complexity: O(n^2). Space complexity: O(1).
     */
    class Solution1 {
        public int subarraySum(int[] nums, int k) {
            int sum = 0, res = 0;
            for (int i = 0; i < nums.length; ++i) {
                for (int j = i; j < nums.length; ++j) {
                    sum += nums[j];
                    // increment res second in forward pass
                    if (sum == k) {
                        ++res;
                    }
                }
                sum -= nums[i];
                ++i;
                if (i >= nums.length) {
                    break;
                }
                for (int j = nums.length - 1; j >= i; --j) {
                    // increment res first in backward pass
                    if (sum == k) {
                        ++res;
                    }
                    sum -= nums[j];
                }
            }
            return res;
        }
    }

    /**
     * Solution 2: Diff
     *
     * Similar to Path Sum III, use a hashmap to remember all sums starting from index 0, and keep track of current sum,
     * then the difference of the two sums is the sum of continuous subarray starting from various indices. Beware of
     * the tricky initial value in the map 0 -> 1 to take care of the case sum == k.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        public int subarraySum(int[] nums, int k) {
            int sum = 0, res = 0;
            Map<Integer, Integer> sum_count = new HashMap<>();
            sum_count.put(0, 1);
            for (int i = 0; i < nums.length; ++i) {
                sum += nums[i];
                res += sum_count.getOrDefault(sum - k, 0);
                sum_count.put(sum, sum_count.getOrDefault(sum, 0) + 1);
            }
            return res;
        }
    }

    /**
     * Solution 3: Diff
     *
     * Clever code reordering to get rid of the 0 -> 1 trick in Solution 2.
     */
    class Solution3 {
        public int subarraySum(int[] nums, int k) {
            int sum = 0, res = 0;
            Map<Integer, Integer> sum_count = new HashMap<>();
            for (int num : nums) {
                sum_count.put(sum, sum_count.getOrDefault(sum, 0) + 1);
                sum += num;
                res += sum_count.getOrDefault(sum - k, 0);
            }
            return res;
        }
    }
}