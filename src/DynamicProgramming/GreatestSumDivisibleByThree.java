/**
 * LeetCode #1262, medium
 *
 * Given an array nums of integers, we need to find the maximum possible sum of elements of the array such that it is
 * divisible by three.
 *
 * Example 1:
 *
 * Input: nums = [3,6,5,1,8]
 * Output: 18
 * Explanation: Pick numbers 3, 6, 1 and 8 their sum is 18 (maximum sum divisible by 3).
 *
 * Example 2:
 *
 * Input: nums = [4]
 * Output: 0
 * Explanation: Since 4 is not divisible by 3, do not pick any number.
 *
 * Example 3:
 *
 * Input: nums = [1,2,3,4,4]
 * Output: 12
 * Explanation: Pick numbers 1, 3, 4 and 4 their sum is 12 (maximum sum divisible by 3).
 *
 * Constraints:
 *
 * 1 <= nums.length <= 4 * 10^4
 * 1 <= nums[i] <= 10^4
 */

package DynamicProgramming;

public class GreatestSumDivisibleByThree {
    /**
     * Solution 1:
     *
     * This is more of a math problem than a programming problem. The basic idea is to sum everything, and exclude
     * certain minimums based on the modulo of the sum:
     *
     * 1) If sum % 3 == 0, of course this is the answer
     * 2) If sum % 3 == 1, we either exclude a mod one number or two mod two numbers
     * 3) If sum % 3 == 2, we either exclude a mod two number or two mod one numbers
     *
     * Just keep track of min of these numbers during the iteration. Note that we can't throw away a second minimum if
     * we find the new minimum! See comment.
     */
    class Solution1 {
        public int maxSumDivThree(int[] nums) {
            int mod_one_min = 100000, mod_one_min2 = 100000;
            int mod_two_min = 100000, mod_two_min2 = 100000;
            int sum = 0;
            for (int num : nums) {
                sum += num;
                switch (num % 3) {
                    case 1:
                        if (num < mod_one_min) {
                            // NB: Next line is critical to make sure we don't throw away a second minimum
                            mod_one_min2 = mod_one_min;
                            mod_one_min = num;
                        } else if (num < mod_one_min2) {
                            mod_one_min2 = num;
                        }
                        break;
                    case 2:
                        if (num < mod_two_min) {
                            // NB: Next line is critical to make sure we don't throw away a second minimum
                            mod_two_min2 = mod_two_min;
                            mod_two_min = num;
                        } else if (num < mod_two_min2) {
                            mod_two_min2 = num;
                        }
                        break;
                    default:
                        break;
                }
            }
            switch (sum % 3) {
                case 0:
                    return sum;
                case 1:
                    return Math.max(sum - mod_one_min, sum - mod_two_min - mod_two_min2);
                case 2:
                    return Math.max(sum - mod_two_min, sum - mod_one_min - mod_one_min2);
                default:
                    break;
            }
            return -1;
        }
    }
}
