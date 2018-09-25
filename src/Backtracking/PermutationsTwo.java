/**
 * LeetCode #47, medium
 *
 * Given a collection of numbers that might contain duplicates, return all possible unique permutations.
 *
 * Example:
 *
 * Input: [1,1,2]
 * Output:
 * [
 *   [1,1,2],
 *   [1,2,1],
 *   [2,1,1]
 * ]
 */

package Backtracking;

import java.util.*;

public class PermutationsTwo {
    /**
     * Solution 1: Backtracking with hash map
     *
     * To deal with duplicates, instead of using boolean array to record if a value is used or not, which won't work
     * for duplicates because switching their usage will yield the same permutation, we can instead use a hash map to
     * count their appearances. For each depth, we loop through the map and check out numbers that have positive
     * counts and proceed to next step. This way, the duplicates will not cause duplicate permutations.
     *
     * Time complexity: O(n! * n). Space complexity: O(n! * n).
     */
    class Solution1 {
        public List<List<Integer>> permuteUnique(int[] nums) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int num : nums) map.put(num, map.getOrDefault(num, 0) + 1);
            List<List<Integer>> res = new ArrayList<>();
            permute(map, new ArrayList<>(), 0, nums.length, res);
            return res;
        }

        private void permute(Map<Integer, Integer> map, List<Integer> cur, int i, int n, List<List<Integer>> res) {
            if (i == n) res.add(new ArrayList<>(cur));
            else {
                for (int num : map.keySet()) {
                    int count = map.get(num);
                    if (count > 0) {
                        map.put(num, count - 1);
                        cur.add(num);
                        permute(map, cur,  i+1, n, res);
                        cur.remove(cur.size() - 1);
                        map.put(num, count);
                    }
                }
            }
        }
    }

    /**
     * Solution 2: Backtracking with sorting
     *
     * Alternatively, we can sort the input array to help skipping duplicates. We will need to make sure that we
     * visit duplicates in certain order, from left to right for example. To achieve that, we sort the array, and
     * every time we see a number that is the same as previous number, it is a duplicate thus may only be visited if
     * its previous brother has been visited. This way it is guaranteed that any duplicates will be visited from left
     * to right, which avoid producing duplicate permutations.
     *
     * Time complexity: O(n! * n). Space complexity: O(n! * n).
     */
    class Solution2 {
        public List<List<Integer>> permuteUnique(int[] nums) {
            Arrays.sort(nums);
            boolean[] used = new boolean[nums.length];
            List<List<Integer>> res = new ArrayList<>();
            permute(nums, used, new ArrayList<>(), 0, nums.length, res);
            return res;
        }

        private void permute(int[] nums, boolean[] used, List<Integer> cur, int count, int n, List<List<Integer>> res) {
            if (count == n) res.add(new ArrayList<>(cur));
            else {
                for (int i = 0; i < nums.length; i++) {
                    if (used[i] || i > 0 && nums[i] == nums[i-1] && !used[i-1]) continue;
                    used[i] = true;
                    cur.add(nums[i]);
                    permute(nums, used, cur, count+1, n, res);
                    cur.remove(cur.size() - 1);
                    used[i] = false;
                }
            }
        }
    }
}
