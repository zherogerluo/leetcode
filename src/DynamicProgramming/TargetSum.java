/**
 * LeetCode #494, medium
 *
 * You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. Now you have 2 symbols + and -.
 * For each integer, you should choose one from + and - as its new symbol.
 *
 * Find out how many ways to assign symbols to make sum of integers equal to target S.
 *
 * Example 1:
 *
 * Input: nums is [1, 1, 1, 1, 1], S is 3.
 * Output: 5
 *
 * Explanation:
 *
 * -1+1+1+1+1 = 3
 * +1-1+1+1+1 = 3
 * +1+1-1+1+1 = 3
 * +1+1+1-1+1 = 3
 * +1+1+1+1-1 = 3
 *
 * There are 5 ways to assign symbols to make the sum of nums be target 3.
 *
 * Constraints:
 *
 * The length of the given array is positive and will not exceed 20.
 * The sum of elements in the given array will not exceed 1000.
 * Your output answer is guaranteed to be fitted in a 32-bit integer.
 */

/* This problem is typical 0/1 knapsack problem */

public class TargetSum {
    /**
     * Solution 1: Brutal force DFS
     *
     * Time complexity: O(2^n). Space complexity: O(log(n)).
     */
    class Solution1 {
        int res;

        public int findTargetSumWays(int[] nums, int S) {
            res = 0;
            helper(nums, 0, 0, S);
            return res;
        }

        private void helper(int[] nums, int i, int sum, final int S) {
            if (i >= nums.length) {
                if (sum == S) {
                    ++res;
                }
                return;
            }
            helper(nums, i + 1, sum + nums[i], S);
            helper(nums, i + 1, sum - nums[i], S);
        }
    }

    /**
     * Solution 2: Enumeration
     *
     * Use a map to record all the appeared sum -> frequency pairs as we iterate through nums.
     *
     * Time complexity: O(n * min(2^n, sum)) where sum is the sum of all numbers in nums array.
     * Space complexity: O(min(2^n, sum))
     */
    class Solution2 {
        public int findTargetSumWays(int[] nums, int S) {
            Map<Integer, Integer> sum_freq = new HashMap<>();
            Map<Integer, Integer> tmp = new HashMap<>();
            sum_freq.put(0, 1);
            for (int i = 0; i < nums.length; ++i) {
                for (int sum : sum_freq.keySet()) {
                    int new_sum = sum + nums[i];
                    tmp.put(new_sum, tmp.getOrDefault(new_sum, 0) + sum_freq.get(sum));
                }
                for (int sum : sum_freq.keySet()) {
                    int new_sum = sum - nums[i];
                    tmp.put(new_sum, tmp.getOrDefault(new_sum, 0) + sum_freq.get(sum));
                }
                Map<Integer, Integer> map = tmp;
                tmp = sum_freq;
                sum_freq = map;
                tmp.clear();
            }
            return sum_freq.getOrDefault(S, 0);
        }
    }

    /**
     * Solution 3: DFS with memoization
     *
     * The brutal force DFS calculates sub-problems repeatedly. Use a map array to memorize seen sum -> freq pairs for
     * numbers upto index i. Beware of the base case.
     *
     * Time complexity: O(n * sum). Space complexity: O(n * sum).
     */
    class Solution3 {
        public int findTargetSumWays(int[] nums, int S) {
            Map[] maps = new Map[nums.length];
            return helper(nums, 0, S, maps);
        }

        private int helper(int[] nums, int i, int target, Map<Integer, Integer>[] memo) {
            if (i >= nums.length) {
                // need to return one if target is eliminated
                return target == 0 ? 1 : 0;
            }
            if (memo[i] != null && memo[i].containsKey(target)) {
                return memo[i].get(target);
            }
            int res1 = helper(nums, i + 1, target + nums[i], memo);
            int res2 = helper(nums, i + 1, target - nums[i], memo);
            if (memo[i] == null) {
                // don't initialize map every time!
                memo[i] = new HashMap<>();
            }
            memo[i].put(target, res1 + res2);
            return res1 + res2;
        }
    }

    /**
     * Solution 4: DFS with memoization
     *
     * Same as Solution 3 except that the memoization data structed is changed to two-dimensional array. The reason is
     * that the sum is a finite value.
     */
    class Solution4 {
        public int findTargetSumWays(int[] nums, int S) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            int[][] memo = new int[nums.length][sum * 2 + 1];
            for (int i = 0; i < nums.length; ++i) {
                for (int j = 0; j < sum * 2 + 1; ++j) {
                    memo[i][j] = -1;
                }
            }
            return helper(nums, 0, S, sum, memo);
        }

        private int helper(int[] nums, int i, int target, final int offset, int[][] memo) {
            if (i >= nums.length) {
                return target == 0 ? 1 : 0;
            }
            if (target + offset < 0 || target + offset >= memo[0].length) {
                return 0;
            }
            if (memo[i][target + offset] != -1) {
                return memo[i][target + offset];
            }
            int res1 = helper(nums, i + 1, target + nums[i], offset, memo);
            int res2 = helper(nums, i + 1, target - nums[i], offset, memo);
            memo[i][target + offset] = res1 + res2;
            return res1 + res2;
        }
    }

    /**
     * Solution 5: Dynamic programming
     *
     * Top-down DP variation of Solution 4. Trick part is the base case, need += not = to take care of nums[0] == 0.
     * Since only memo[i-1] is needed, one can use two rolling arrays to reduce space complexity.
     */
    class Solution5 {
        public int findTargetSumWays(int[] nums, int S) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            if (S > sum || S < -sum) {
                return 0;
            }
            final int m = nums.length;
            final int n = sum * 2 + 1;
            int[][] memo = new int[m][n];
            memo[0][sum - nums[0]] += 1;
            memo[0][sum + nums[0]] += 1;
            for (int i = 1; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    memo[i][j] =
                        (j - nums[i] >= 0 ? memo[i-1][j - nums[i]] : 0) +
                        (j + nums[i] < n  ? memo[i-1][j + nums[i]] : 0);
                }
            }
            return memo[m-1][sum + S];
        }
    }
}