/**
 * LeetCode #300, medium
 *
 * Given an unsorted array of integers, find the length of longest increasing subsequence.

 Example:

 Input: [10,9,2,5,3,7,101,18]
 Output: 4
 Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
 Note:

 There may be more than one LIS combination, it is only necessary for you to return the length.
 Your algorithm should run in O(n2) complexity.
 Follow up: Could you improve it to O(n log n) time complexity?
 */

package DynamicProgramming;

public class LongestIncreasingSubsequence {
    /**
     * Solution 1: Dynamic programming
     *
     * Define dp[i] = length of LIS ending at index i, then the formula is:
     *
     * dp[i] = max(1 + dp[j] for j < i where nums[j] < nums[i])
     *
     * Unlike typical DP problem where the final result is the last element, for this problem DP array is defined as
     * lengths of LIS ending at index i, so in order to find the length of LIS for the entire array, we need to keep
     * track of a max and update max for every i. The final result will be this max.
     *
     * Time complexity: O(n^2). Space complexity: O(n).
     */
    class Solution1 {
        public int lengthOfLIS(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            int[] lengths = new int[nums.length];
            lengths[0] = 1;
            int max = 1;
            for (int i = 1; i < nums.length; i++) {
                int len = 1;
                for (int j = 0; j < i; j++) {
                    if (nums[j] < nums[i]) len = Math.max(len, 1 + lengths[j]);
                }
                lengths[i] = len;
                max = Math.max(max, len);
            }
            return max;
        }
    }

    /**
     * Solution 2: Dynamic programming with binary search
     *
     * This time DP array is named "tails" and stores the smallest tail number of all LIS of length i+1. Then it is
     * easy to see that tails are increasing, thus we can use binary search to easily update the tails. For a new
     * number, we search the tail that is just immediately larger than this number, and we know we need to update
     * this tail because a new LIS of this length can be formed by the LIS of length-1 plus this new number and this
     * new LIS will have a smaller tail. If the search falls out of range, we increment the range and assign number
     * as tail for this newly allocated tail slot.
     *
     * The tricky part is the binary search. Recommend to use exclusive right side pointer, after the search, right
     * side pointer will be the index of first tail that is larger than (or equal to) num.
     *
     * Time complexity: O(n * log(n)). Space complexity: O(n).
     */
    class Solution2 {
        public int lengthOfLIS(int[] nums) {
            // tails stores smallest tail number of LIS of length i+1
            if (nums == null || nums.length == 0) return 0;
            int[] tails = new int[nums.length];
            tails[0] = nums[0];
            int max = 1;
            for (int num : nums) {
                int i = 0, j = max, mid;
                // binary search
                while (i < j) {
                    mid = i + (j - i) / 2;
                    if (tails[mid] < num) i = mid + 1;
                    else j = mid;
                }
                // j is the first index where tail[j] >= num
                tails[j] = num;
                max = Math.max(j + 1, max); // increment max if j + 1 is out of range
            }
            return max;
        }
    }
}
