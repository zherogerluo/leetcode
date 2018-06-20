/**
 * LeetCode #46, medium
 *
 * Given a collection of distinct integers, return all possible permutations.

 Example:

 Input: [1,2,3]
 Output:
 [
 [1,2,3],
 [1,3,2],
 [2,1,3],
 [2,3,1],
 [3,1,2],
 [3,2,1]
 ]
 */

package Backtracking;

import java.util.*;

public class Permutations {
    /**
     * Solution 1: Backtracking
     *
     * Typical backtracking, building the list from unused numbers, collect the result once it reaches nums.length
     * size. One trick is to use a boolean array to remember which one has been used, that way we don't have to use
     * List::contains method which costs additional time.
     *
     * Time complexity: O(n!). Space complexity: O(n!). We will have n! permutations.
     */
    class Solution1 {
        public List<List<Integer>> permute(int[] nums) {
            boolean[] used = new boolean[nums.length];
            List<List<Integer>> res = new ArrayList<>();
            permute(nums, used, new ArrayList<Integer>(), res);
            return res;
        }

        private void permute(int[] nums, boolean[] used, List<Integer> list, List<List<Integer>> res) {
            if (list.size() == nums.length) res.add(new ArrayList<Integer>(list));
            for (int i = 0; i < nums.length; i++) {
                if (!used[i]) {
                    used[i] = true;
                    list.add(nums[i]);
                    permute(nums, used, list, res);
                    used[i] = false;
                    list.remove(list.size()-1);
                }
            }
        }
    }

    /**
     * Solution 2: Iterative
     *
     * If we know the permutations of n-1 elements, we simply add the nth element to all n-1 permutation at all
     * possible locations to get permutations of n. Recursion or dynamic programming idea, but can be implemented
     * with iterative programming here.
     */
    class Solution2 {
        public List<List<Integer>> permute(int[] nums) {
            LinkedList<List<Integer>> res = new LinkedList<>();
            res.add(new ArrayList<Integer>());
            for (int num : nums) {
                int size = res.size();
                for (int i = 0; i < size; i++) {
                    List<Integer> list = res.poll();
                    for (int j = 0; j <= list.size(); j++) {
                        List<Integer> tempList = new ArrayList<>(list);
                        tempList.add(j, num);
                        res.offer(tempList);
                    }
                }
            }
            return res;
        }
    }
}
