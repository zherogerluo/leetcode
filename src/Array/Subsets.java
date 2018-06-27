/**
 * LeetCode #78, medium
 *
 * Given a set of distinct integers, nums, return all possible subsets (the power set).

 Note: The solution set must not contain duplicate subsets.

 Example:

 Input: nums = [1,2,3]
 Output:
 [
 [3],
 [1],
 [2],
 [1,2,3],
 [1,3],
 [2,3],
 [1,2],
 []
 ]
 */

package Array;

import java.util.*;

public class Subsets {
    /**
     * Solution 1: Backtracking
     *
     * Typical backtracking, very similar to Problem #77.
     *
     * Time complexity: O(2^n). Space complexity: O(2^n). Total number of subsets is 2^n.
     */
    class Solution1 {
        public List<List<Integer>> subsets(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            subsets(nums, 0, new ArrayList<Integer>(), res);
            return res;
        }

        private void subsets(int[] nums, int i, List<Integer> temp, List<List<Integer>> res) {
            res.add(new ArrayList<>(temp)); // need to add temp to results every time
            for (; i < nums.length; i++) {
                temp.add(nums[i]);
                subsets(nums, i+1, temp, res);
                temp.remove(temp.size()-1);
            }
        }
    }

    /**
     * Solution 2: Iterative
     *
     * For every new number in nums, appended it to the already existing solutions in res and add them to the end of
     * res. This solution builds the subsets of nums[0-i] while incrementing i. It is the same idea as using a queue
     * to hold temporary results and append new numbers to them, but since every element coming out of the queue is
     * still part of solution, we don't need to use a queue, just res list itself would be sufficient.
     *
     * Tricky part: We need to record the initial size of res every time before kicking off the inner loop.
     */
    class Solution2 {
        public List<List<Integer>> subsets(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            res.add(new ArrayList<>());
            for (int i = 0; i < nums.length; i++){
                int size = res.size(); // need to record the initial size
                for (int j = 0; j < size; j++){
                    // get each result list, make copy and append new number to the end
                    List<Integer> temp = new ArrayList<>(res.get(j));
                    temp.add(nums[i]);
                    res.add(temp);
                }
            }
            return res;
        }
    }
}
