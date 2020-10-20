/**
 * LeetCode #763, medium
 *
 * A string S of lowercase English letters is given. We want to partition this string into as many parts as possible
 * so that each letter appears in at most one part, and return a list of integers representing the size of these parts.
 *
 * Example 1:
 *
 * Input: S = "ababcbacadefegdehijhklij"
 * Output: [9,7,8]
 *
 * Explanation:
 * The partition is "ababcbaca", "defegde", "hijhklij".
 * This is a partition so that each letter appears in at most one part.
 * A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
 *
 * Note:
 *
 * S will have length in range [1, 500].
 * S will consist of lowercase English letters ('a' to 'z') only.
 */

import java.util.*;

public class PartitionLabels {
    /**
     * Solution 1:
     *
     * Observation: Same letter has to be in the same partition. Based on this observation, we can convert the string
     * into intervals and merge them to get the partition.
     *
     * Time complexity: O(n) (not O(m * n) because we have at most 26 intervals)
     * Space complexity: O(n)
     */
    class Solution1 {
        public List<Integer> partitionLabels(String S) {
            // convert to interval per character
            int[] start = new int[26];
            int[] end = new int[26];
            for (int i = 0; i < S.length(); i++) {
                int idx = S.charAt(i) - 'a';
                if (start[idx] == 0) {
                    start[idx] = i + 1;
                }
                end[idx] = i + 2; // note we need to always update the end index (for single character interval)
            }
            // merge intervals to obtain partition
            // sort first
            List<int[]> intervals = new ArrayList<>();
            for (int i = 0; i < start.length; i++) {
                if (start[i] != 0) {
                    intervals.add(new int[]{ start[i], end[i] });
                }
            }
            intervals.sort((a, b) -> Integer.compare(a[0], b[0]));
            // merge one by one
            int[] last = null;
            List<Integer> result = new ArrayList<>();
            for (int[] curr : intervals) {
                if (last == null) {
                    last = curr;
                } else if (last[0] == curr[0] || last[1] > curr[0]) {
                    last[1] = Math.max(last[1], curr[1]);
                } else {
                    result.add(last[1] - last[0]);
                    last = curr;
                }
            }
            result.add(last[1] - last[0]); // remember to add the remaining interval
            return result;
        }
    }

    /**
     * Solution 2:
     *
     * Same idea as Solution 1, except some optimizations on the intervals:
     *
     * Optimization 1: We don't need to remember the starting position of intervals, because we can easily get them
     * when we iterate through the string.
     *
     * Optimization 2: We don't need to sort the intervals based on their starting positions, because they are already
     * sorted when we iterate through the string.
     *
     * When merging the intervals, we remember and update the farthest end until the index reaches it, which directly
     * comes from the definition of a smallest partition.
     */
    class Solution2 {
        public List<Integer> partitionLabels(String S) {
            // convert to intervals, start is in the string itself
            int[] end = new int[26];
            for (int i = 0; i < S.length(); i++) {
                end[S.charAt(i) - 'a'] = i + 1;
            }
            // no need to sort the intervals since start is naturally sorted
            // in the string
            int l = 0, r = 1; // left and right index of current partition
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < S.length(); i++) {
                // extend the partition every time we see a further end
                r = Math.max(r, end[S.charAt(i) - 'a']);
                if (r == i + 1) {
                    // invariant: all the letters in the partition have to end
                    // at the partition boundary
                    result.add(r - l);
                    l = i + 1;
                }
            }
            return result;
        }
    }
}
