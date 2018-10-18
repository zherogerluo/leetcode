/**
 * LeetCode #416, medium
 *
 * Given a non-empty array containing only positive integers, find if the array can be partitioned into two subsets
 * such that the sum of elements in both subsets is equal.
 *
 * Note:
 * Each of the array element will not exceed 100.
 * The array size will not exceed 200.
 *
 * Example 1:
 *
 * Input: [1, 5, 11, 5]
 *
 * Output: true
 *
 * Explanation: The array can be partitioned as [1, 5, 5] and [11].
 *
 * Example 2:
 *
 * Input: [1, 2, 3, 5]
 *
 * Output: false
 *
 * Explanation: The array cannot be partitioned into equal sum subsets.
 */

package DynamicProgramming;

import java.util.*;

public class PartitionEqualSubsetSum {
    /**
     * Solution 1: DFS with optimization
     *
     * 0/1 knapsack problem, can be solved using DFS. In this case we need to find the sum and the problem reduces to
     * whether we can find elements that sum up to target=sum/2. Optimization we can utilize is to sort the array and
     * start from the largest number. However this does not change the fact that this algorithm is O(2^n).
     *
     * Time complexity: O(2^n). Space complexity: O(n).
     */
    class Solution1 {
        public boolean canPartition(int[] nums) {
            int sum = 0;
            for (int num : nums) sum += num;
            if (sum % 2 != 0) return false;
            Arrays.sort(nums);
            return canFindSum(nums, nums.length, sum / 2);
        }

        private boolean canFindSum(int[] nums, int index, int target) {
            if (index < 1 || nums[index-1] > target) return false;
            if (nums[index-1] == target) return true;
            return canFindSum(nums, index-1, target-nums[index-1]) ||
                   canFindSum(nums, index-1, target);
        }
    }

    /**
     * Solution 2: DFS with memoization, top-down DP
     *
     * Using memoization, we can reduce the run time to O(n * sum) which is better than O(2^n) if n is sufficiently
     * large. No optimization is implemented here, although it might help to further improve the performance. The
     * memo array simply defines whether the target sum can be found with elements from 0 to certain index, which is
     * the definition of the helper function.
     *
     * Time complexity: O(n * sum). Space complexity: O(n * sum).
     */
    class Solution2 {
        public boolean canPartition(int[] nums) {
            int sum = 0;
            for (int num : nums) sum += num;
            if (sum % 2 != 0) return false;
            sum /= 2;
            return canFindSum(nums, 0, sum, new int[nums.length][sum+1]);
        }

        private boolean canFindSum(int[] nums, int index, int target, int[][] memo) {
            if (index >= nums.length || nums[index] > target) return false;
            if (nums[index] == target) return true;
            if (memo[index][target] != 0) return memo[index][target] == 1;
            boolean result = canFindSum(nums, index+1, target-nums[index], memo) ||
                    canFindSum(nums, index+1, target, memo);
            memo[index][target] = result ? 1 : -1;
            return result;
        }
    }

    /**
     * Solution 3: Dynamic programming
     *
     * Bottom-up DP, results[i][j] is defined as whether sum j can be achieved using elements whose index is up to i.
     * For index i, the result is either results[i-1][j] if we don't pick it, or results[i-1][j-num] if we pick it.
     *
     * Formula: results[i][j] = results[i-1][j] || results[i-1][j-nums[i]]
     *
     * The base cases are results[i][0] = true (we can always make zero sum by picking none).
     *
     * Time complexity: O(n * sum). Space complexity: O(n * sum).
     */
    class Solution3 {
        public boolean canPartition(int[] nums) {
            int sum = 0;
            for (int num : nums) sum += num;
            if (sum % 2 != 0) return false;
            sum /= 2;
            boolean[][] results = new boolean[nums.length][sum+1];
            if (nums[0] > sum) return false; // check for bound
            results[0][0] = true; results[0][nums[0]] = true; // for the first element
            for (int i = 1; i < nums.length; i++) { // note i starts from 1, otherwise i-1 is out of bound
                results[i][0] = true;
                for (int j = nums[i]; j <= sum; j++) {
                    results[i][j] = results[i-1][j] || results[i-1][j-nums[i]];
                }
            }
            return results[nums.length-1][sum];
        }
    }

    /**
     * Solution 4: Dynamic programming
     *
     * We can optimize space usage from Solution 3 by using a one dimensional array, because DP algorithm uses only
     * last pass data. But note that we need to start from end of array to beginning, to avoid using already updated
     * value from this pass.
     *
     * Time complexity: O(n * sum). Space complexity: O(sum).
     */
    class Solution4 {
        public boolean canPartition(int[] nums) {
            int sum = 0;
            for (int num : nums) sum += num;
            if (sum % 2 != 0) return false;
            sum /= 2;
            boolean[] results = new boolean[sum+1];
            results[0] = true;
            for (int num : nums) {
                for (int j = sum; j >= num; j--) { // iterate backwards, otherwise j-num will already be updated
                    results[j] = results[j] || results[j-num];
                }
            }
            return results[sum];
        }
    }
}
