/**
 * LeetCode #128, hard
 *
 * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

 Your algorithm should run in O(n) complexity.

 Example:

 Input: [100, 4, 200, 1, 3, 2]
 Output: 4
 Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
 */

package Array;

import java.util.*;

public class LongestConsecutiveSequence {
    /**
     * Solution 1: Hash map
     *
     * For a number k, we can easily check if k+1 is in nums using hash map in O(1) time. Hash map can also store
     * result for this number, avoiding redundant work.
     *
     * Time complexity: O(n) because the longest consecutive for every number is calculated exactly once and then
     * cached in the hash map.
     *
     * Space complexity: O(n).
     */
    class Solution1 {
        public int longestConsecutive(int[] nums) {
            Map<Integer, Integer> memo = new HashMap<>();
            for (int num : nums) memo.put(num, 0);
            int res = 0;
            for (int num : nums) {
                res = Math.max(res, longestConsecutive(num, memo));
            }
            return res;
        }

        private int longestConsecutive(int num, Map<Integer, Integer> memo) {
            if (!memo.containsKey(num)) return 0;
            int len = memo.get(num);
            if (len > 0) return len;
            len = 1 + longestConsecutive(num + 1, memo);
            memo.put(num, len);
            return len;
        }
    }
\

    /**
     * Solution 2: Elegant solution using hash set
     *
     * Simply walk through the streak if the number is the start of a streak, i.e. num-1 is not in the numbers. The
     * same streak will be walked through just once, and no substreak will be walked, so this solution is O(n). Very
     * elegant solution.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        public int longestConsecutive(int[] nums) {
            Set<Integer> set = new HashSet<>();
            int res = 0;
            for (int num : nums) set.add(num);
            for (int num : nums) {
                if (!set.contains(num - 1)) {
                    int len = 1;
                    while (set.contains(++num)) len++;
                    res = Math.max(res, len);
                }
            }
            return res;
        }
    }

    /**
     * Solution 3: Sorting
     *
     * Simply sort the array and walk through it. Not trivial, there are some traps: 1) Need to handle corner cases
     * such as 0 and 1 lengths; 2) res should be initialized as one; 3) Need to skip the duplicates; 4) If we update
     * result only when finding non-consecutive number, we need to also update result after the entire iteration.
     *
     * Time complexity: O(n * log(n)). Space complexity: O(1).
     */
    class Solution3 {
        public int longestConsecutive(int[] nums) {
            if (nums == null || nums.length == 0) return 0; // corner cases
            Arrays.sort(nums);
            int len = 1, res = 1; // both are initialized to 1
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] == nums[i-1]) continue; // skip duplicates
                if (nums[i] == nums[i-1] + 1) len++;
                else len = 1;
                res = Math.max(res, len); // update res for every loop, prevent bug in the next line
            }
            // don't forget to collect last result. alternatively can update res for every loop.
            return res; //Math.max(res, len);
        }
    }
}
