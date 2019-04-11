/**
 * LeetCode #40, medium
 *
 * Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in
 * candidates where the candidate numbers sums to target.
 *
 * Each number in candidates may only be used once in the combination.
 *
 * Note:
 *
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * Example 1:
 *
 * Input: candidates = [10,1,2,7,6,1,5], target = 8,
 * A solution set is:
 * [
 *   [1, 7],
 *   [1, 2, 5],
 *   [2, 6],
 *   [1, 1, 6]
 * ]
 *
 * Example 2:
 *
 * Input: candidates = [2,5,2,1,2], target = 5,
 * A solution set is:
 * [
 *   [1,2,2],
 *   [5]
 * ]
 */

package Array;

import java.util.*;

public class CombinationSumTwo {
    /**
     * Solution 1: Backtracking
     *
     * Same as Combination Sum except that the candidates are sorted to help avoid duplicates. In the DFS process,
     * skip all duplicates after the search.
     */
    class Solution1 {
        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            Arrays.sort(candidates); // sort to deal with duplicates
            List<List<Integer>> all = new ArrayList<>();
            search(candidates, target, 0, new ArrayList<>(), all);
            return all;
        }

        private void search(int[] nums, int target, int i, List<Integer> cur, List<List<Integer>> all) {
            if (i > nums.length || target < 0) return;
            if (target == 0) {
                all.add(new ArrayList<>(cur));
                return;
            }
            for (; i < nums.length; i++) {
                cur.add(nums[i]);
                search(nums, target-nums[i], i+1, cur, all);
                cur.remove(cur.size()-1);
                for (; i < nums.length-1 && nums[i+1] == nums[i]; i++); // skip duplicates in the sorted array
            }
        }
    }
}
