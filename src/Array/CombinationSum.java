/**
 * LeetCode #39, medium
 *
 * Given a set of candidate numbers (candidates) (without duplicates) and a target number (target), find all unique
 * combinations in candidates where the candidate numbers sums to target.
 *
 * The same repeated number may be chosen from candidates unlimited number of times.
 *
 * Note:
 *
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * Example 1:
 *
 * Input: candidates = [2,3,6,7], target = 7,
 * A solution set is:
 * [
 *   [7],
 *   [2,2,3]
 * ]
 * Example 2:
 *
 * Input: candidates = [2,3,5], target = 8,
 * A solution set is:
 * [
 *   [2,2,2,2],
 *   [2,3,3],
 *   [3,5]
 * ]
 */

package Array;

import java.util.*;

public class CombinationSum {
    /**
     * Solution 1: Backtracking
     *
     * Typical backtracking. To avoid duplicates however, we allow next step to reuse only the number in the last
     * step. To achieve this, we can keep track of the last index visited, and only iterate on later elements. Note
     * that we don't actually need to sort the array.
     */
    class Solution1 {
        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            List<List<Integer>> all = new ArrayList<>();
            solve(candidates, 0, 0, target, new ArrayList<>(), all);
            return all;
        }

        private void solve(int[] candidates, int curIndex, int curSum, int target,
                           List<Integer> curList, List<List<Integer>> all) {
            if (curSum > target) return;
            if (curSum == target) {
                all.add(new ArrayList<>(curList));
                return;
            }
            for (; curIndex < candidates.length; curIndex++) {
                int num = candidates[curIndex];
                curList.add(num);
                solve(candidates, curIndex, curSum + num, target, curList, all);
                curList.remove(curList.size()-1);
            }
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * Use DP to solve for all targets from 1 to target. For each new target, we iterate through candidates and
     * append them to the remaining, i.e. target - candidate, to form new results for this new target:
     *
     * results[t] = [result + c for every result in results[i] and every c in candidates where i = t - c]
     *
     * Note another DP idea is to find all combinations for targets[i] and targets[j] where i + j == t. But this is
     * more difficult and perhaps less efficient, but worst, hard to avoid duplicates.
     */
    class Solution2 {
        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            List<List<Integer>>[] results = new List[target+1];
            // initial condition of target == 0
            results[0] = new ArrayList<>();
            results[0].add(new ArrayList<>());
            for (int t = 1; t <= target; t++) {
                for (int num : candidates) {
                    int remain = t - num;
                    if (remain < 0 || results[remain] == null) continue; // check for remain < 0
                    for (List<Integer> list : results[remain]) {
                        if (list.isEmpty() || list.get(0) >= num) { // enforce ascending order by value comparison
                            List<Integer> newList = new ArrayList<>();
                            newList.add(num);
                            newList.addAll(list);
                            if (results[t] == null) results[t] = new ArrayList<>();
                            results[t].add(newList);
                        }
                    }
                }
            }
            return results[target] == null ? new ArrayList<>() : results[target];
        }
    }
}
