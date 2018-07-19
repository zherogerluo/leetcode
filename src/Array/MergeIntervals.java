/**
 * LeetCode #56, medium
 *
 * Given a collection of intervals, merge all overlapping intervals.

 Example 1:

 Input: [[1,3],[2,6],[8,10],[15,18]]
 Output: [[1,6],[8,10],[15,18]]
 Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
 Example 2:

 Input: [[1,4],[4,5]]
 Output: [[1,5]]
 Explanation: Intervals [1,4] and [4,5] are considerred overlapping.
 */

package Array;

import java.util.*;

public class MergeIntervals {
    /**
     * Definition for an interval.
     */
    public class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }

    /**
     * Solution 1: Sorting
     *
     * Sort the intervals by its start, then merge one by one. Be careful when writing merging algorithm: One
     * interval may contain another.
     *
     * Time complexity: O(n * log(n)). Space complexity: O(n).
     */
    class Solution1 {
        public List<Interval> merge(List<Interval> intervals) {
            intervals.sort((a, b) -> Integer.compare(a.start, b.start));
            Interval last = null;
            List<Interval> res = new ArrayList<>();
            for (Interval cur : intervals) {
                if (last == null) {
                    last = cur;
                    continue;
                }
                // merge last and cur
                if (last.end < cur.start) {
                    res.add(last);
                    last = cur;
                } else {
                    // end point of new interval is max of the two
                    last = new Interval(last.start, Math.max(last.end, cur.end));
                }
            }
            if (last != null) res.add(last); // remember to pick up the remaining last one
            return res;
        }
    }
}
