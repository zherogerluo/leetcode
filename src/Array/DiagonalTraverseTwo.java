/**
 * LeetCode #1424, medium
 *
 * Given a list of lists of integers, nums, return all elements of nums in diagonal order as shown in the below images.
 *
 * Example 1:
 * Input: nums = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [1,4,2,7,5,3,8,6,9]
 *
 * Example 2:
 * Input: nums = [[1,2,3,4,5],[6,7],[8],[9,10,11],[12,13,14,15,16]]
 * Output: [1,6,2,8,7,3,9,4,12,10,5,13,11,14,15,16]
 *
 * Example 3:
 * Input: nums = [[1,2,3],[4],[5,6,7],[8],[9,10,11]]
 * Output: [1,4,2,5,3,8,6,9,7,10,11]
 *
 * Example 4:
 * Input: nums = [[1,2,3,4,5,6]]
 * Output: [1,2,3,4,5,6]
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i].length <= 10^5
 * 1 <= nums[i][j] <= 10^9
 * There at most 10^5 elements in nums.
 */

package Array;

import java.util.*;

public class DiagonalTraverseTwo {
    /**
     * Solution 1:
     *
     * Queue up the iterators of each list, process head and put it in the back of queue until queue is empty. This is
     * like the BFS approach, except that we need to add iterator one at a time initially.
     *
     * Time complexity: O(n). Space complexity: O(nums.size()) not counting results.
     */
    class Solution1 {
        public int[] findDiagonalOrder(List<List<Integer>> nums) {
            List<Integer> res = new ArrayList<>();
            Deque<Iterator<Integer>> iters = new ArrayDeque<>();
            Iterator<List<Integer>> numsIter = nums.iterator();
            iters.offerFirst(numsIter.next().iterator());
            while (!iters.isEmpty()) {
                for (int size = iters.size(); size > 0; --size) {
                    Iterator<Integer> iter = iters.removeFirst();
                    if (iter.hasNext()) {
                        res.add(iter.next());
                        iters.offerLast(iter);
                    }
                }
                if (numsIter.hasNext()) {
                    iters.offerFirst(numsIter.next().iterator());
                }
            }
            int[] arr = new int[res.size()];
            int index = 0;
            for (int num : res) {
                arr[index++] = num;
            }
            return arr;
        }
    }
}
