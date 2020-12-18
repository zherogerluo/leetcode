/**
 * LeetCode #491, medium
 *
 * Given an integer array, your task is to find all the different possible increasing subsequences of the given array,
 * and the length of an increasing subsequence should be at least 2.
 *
 * Example:
 *
 * Input: [4, 6, 7, 7]
 * Output: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]
 *
 * Constraints:
 *
 * The length of the given array will not exceed 15.
 * The range of integer in the given array is [-100,100].
 * The given array may contain duplicates, and two equal integers should also be considered as a special case of
 * increasing sequence.
 */

import java.util.*;

public class IncreasingSubsequences {
    /**
     * Solution 1: Depth-first search
     *
     * DFS with binary decision at index i (whether or not to put index i into tmp). This one is prone to duplicated
     * results, therefore a set is used to deduplicate results.
     */
    class Solution1 {
        public List<List<Integer>> findSubsequences(int[] nums) {
            Set<List<Integer>> res = new HashSet<>();
            helper(nums, 0, new ArrayList<>(), res);
            return new ArrayList<>(res);
        }

        private void helper(int[] nums, int i, List<Integer> tmp, Set<List<Integer>> res) {
            if (i == nums.length) {
                return;
            }
            helper(nums, i+1, tmp, res);
            if (tmp.isEmpty() || nums[i] >= tmp.get(tmp.size()-1)) {
                tmp.add(nums[i]);
                if (tmp.size() >= 2) {
                    res.add(new ArrayList<>(tmp));
                }
                helper(nums, i+1, tmp, res);
                tmp.remove(tmp.size()-1);
            }
        }
    }

    /**
     * Solution 2: DFS
     *
     * DFS with iterative decision for index i, looping through rest of nums. This way it is easy to track duplicates
     * using a set.
     */
    class Solution2 {
        public List<List<Integer>> findSubsequences(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            helper(nums, 0, new ArrayList<>(), res);
            return new ArrayList<>(res);
        }

        private void helper(int[] nums, int i, List<Integer> tmp, List<List<Integer>> res) {
            if (i == nums.length) {
                return;
            }
            Set<Integer> set = new HashSet<>();
            for (; i < nums.length; ++i) {
                if (tmp.isEmpty() || nums[i] >= tmp.get(tmp.size()-1)) {
                    if (set.contains(nums[i])) {
                        continue;
                    }
                    tmp.add(nums[i]);
                    set.add(nums[i]);
                    if (tmp.size() >= 2) {
                        res.add(new ArrayList<>(tmp));
                    }
                    helper(nums, i+1, tmp, res);
                    tmp.remove(tmp.size()-1);
                }
            }
        }
    }
}
