/**
 * LeetCode #57, hard
 *
 * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
 *
 * You may assume that the intervals were initially sorted according to their start times.
 *
 * Example 1:
 *
 * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
 * Output: [[1,5],[6,9]]
 *
 * Example 2:
 *
 * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * Output: [[1,2],[3,10],[12,16]]
 * Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
 */

package Array;

import java.util.*;

public class InsertInterval {
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
     * Solution 1: Linear search and merge
     *
     * One can use binary search to locate the position where newInterval would be inserted, but building the output
     * array still takes O(n), so no need for binary search here. Instead we simply iterate through entire array and
     * merge interval as needed.
     *
     * Time complexity: O(n). Space complexity: O(1) not counting output.
     */
    class Solution1 {
        public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
            List<Interval> result = new ArrayList<>();
            for (Iterator<Interval> iter = intervals.iterator(); iter.hasNext();) {
                Interval next = iter.next();
                if (isOverlap(next, newInterval)) newInterval = merge(next, newInterval);
                else if (next.end < newInterval.start) result.add(next);
                else {
                    result.add(newInterval);
                    result.add(next);
                    while (iter.hasNext()) result.add(iter.next());
                    return result;
                }
            }
            result.add(newInterval);
            return result;
        }

        private boolean isOverlap(Interval a, Interval b) {
            return !(a.end < b.start || b.end < a.start);
        }

        private Interval merge(Interval a, Interval b) {
            return new Interval(Math.min(a.start, b.start), Math.max(a.end, b.end));
        }
    }

    /**
     * Solution 2:
     *
     * Variant of Solution 1, using null to flag that no further merging is needed.
     */
    class Solution2 {
        public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
            List<Interval> result = new ArrayList<>();
            for (Interval a : intervals) {
                if (newInterval == null || a.end < newInterval.start) result.add(a);
                else if (a.start > newInterval.end) {
                    result.add(newInterval);
                    result.add(a);
                    newInterval = null; // flag -> no more overlaps, no further merging needed
                } else {
                    newInterval = new Interval(Math.min(a.start, newInterval.start),
                                               Math.max(a.end, newInterval.end));
                }
            }
            if (newInterval != null) result.add(newInterval); // don't forget tail
            return result;
        }
    }
}
