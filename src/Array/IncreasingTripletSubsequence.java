/**
 * LeetCode #334, medium
 *
 * Given an unsorted array return whether an increasing subsequence of length 3 exists or not in the array.
 *
 * Formally the function should:
 *
 * Return true if there exists i, j, k
 * such that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return false.
 * Note: Your algorithm should run in O(n) time complexity and O(1) space complexity.
 *
 * Example 1:
 *
 * Input: [1,2,3,4,5]
 * Output: true
 * Example 2:
 *
 * Input: [5,4,3,2,1]
 * Output: false
 */

package Array;

public class IncreasingTripletSubsequence {
    /**
     * Solution 1:
     *
     * Simply record min and mid number seen so far, and update them once sees smaller ones. Return true if we find a
     * number that is larger than both.
     *
     * The generalized version for increasing k length subsequence is identical to Problem #300 (longest increasing
     * subsequence), and the solution here is also similar to the O(n * log(n)) solution using tail array. Here, min
     * and mid serve as tail array.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution1 {
        public boolean increasingTriplet(int[] nums) {
            int min = Integer.MAX_VALUE, mid = Integer.MAX_VALUE;
            for (int num : nums) {
                if (num <= min) min = num;
                else if (num <= mid) mid = num;
                else return true;
            }
            return false;
        }
    }
}
