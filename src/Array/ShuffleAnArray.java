/**
 * LeetCode #384, medium
 *
 * Shuffle a set of numbers without duplicates.
 *
 * Example:
 *
 * // Init an array with set 1, 2, and 3.
 * int[] nums = {1,2,3};
 * Solution solution = new Solution(nums);
 *
 * // Shuffle the array [1,2,3] and return its result. Any permutation of [1,2,3] must equally likely to be returned.
 * solution.shuffle();
 *
 * // Resets the array back to its original configuration [1,2,3].
 * solution.reset();
 *
 * // Returns the random shuffling of array [1,2,3].
 * solution.shuffle();
 */

package Array;

import java.util.*;

public class ShuffleAnArray {
    /**
     * Solution 1:
     *
     * When shuffling, put each number in the shuffled array randomly, if a number is already there, try again until
     * a slot is found. In this process, we keep a visited array to track the slot occupation.
     *
     * For the kth number, the expectation of number of random tries is n/(n-k+1) because the probability of putting it
     * in an empty slot is (n-k+1)/n. So the total number of operation is n/n + n/(n-1) + ... + n/(n-k+1) + ... + n/2
     * + n/1 = n * (1 + 1/2 + 1/3 + ... + 1/k + ... + 1/n), the latter part of which is the partial sum of the
     * harmonic series. It is known to be growing in logarithm speed, so the total time complexity of this shuffling
     * process is O(n * log(n)). Space complexity is O(n).
     */
    class Solution1 {
        int[] nums;
        int[] shuffled;

        public Solution1(int[] nums) {
            this.nums = nums;
            shuffled = new int[nums.length];
        }

        /** Resets the array to its original configuration and return it. */
        public int[] reset() {
            return nums;
        }

        /** Returns a random shuffling of the array. */
        public int[] shuffle() {
            Random random = new Random();
            boolean[] visited = new boolean[nums.length];
            for (int i = 0; i < nums.length; i++) {
                int j = 0;
                while (visited[j = random.nextInt(nums.length)]);
                shuffled[j] = nums[i];
                visited[j] = true;
            }
            return shuffled;
        }
    }

    /**
     * Solution 2: Fisher-Yates shuffle
     *
     * This algorithm guarantees uniform probability distribution in O(n) time. The idea is to start from back of
     * array (i = n-1), generate random index with inclusive bound of [0, i], and swap ith element with the random
     * position element.
     *
     * A quick proof: For i = n-1 to have an element v, the probability is apparently 1/n which is good. For i = n-2
     * to have v, the probability would be (pick v from n-1 elements && v is not picked from i = n-1 case) = 1/(n-1) *
     * (n-1)/n = 1/n. In general, the probability for i = k to have v is (pick v from k elements && v is not pick
     * from i = n-1 && n-2 && ... && k+1 cases) = 1/k * k/(k+1) * ... * (n-1)/n = 1/n.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        int[] nums;
        int[] shuffled;

        public Solution2(int[] nums) {
            this.nums = nums;
            shuffled = Arrays.copyOf(nums, nums.length);
        }

        /** Resets the array to its original configuration and return it. */
        public int[] reset() {
            return nums;
        }

        /** Returns a random shuffling of the array. */
        public int[] shuffle() {
            Random random = new Random();
            for (int i = nums.length-1; i >= 0; i--) {
                swap(shuffled, i, random.nextInt(i+1));
            }
            return shuffled;
        }

        private void swap(int[] nums, int i, int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }
}
