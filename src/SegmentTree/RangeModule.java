/**
 * LeetCode #715, hard
 *
 * A Range Module is a module that tracks ranges of numbers. Your task is to design and implement the following
 * interfaces in an efficient manner.
 *
 * addRange(int left, int right) Adds the half-open interval [left, right), tracking every real number in that interval.
 * Adding an interval that partially overlaps with currently tracked numbers should add any numbers in the interval
 * [left, right) that are not already tracked.
 * queryRange(int left, int right) Returns true if and only if every real number in the interval [left, right) is
 * currently being tracked.
 * removeRange(int left, int right) Stops tracking every real number currently being tracked in the interval
 * [left, right).
 *
 * Example 1:
 * addRange(10, 20): null
 * removeRange(14, 16): null
 * queryRange(10, 14): true (Every number in [10, 14) is being tracked)
 * queryRange(13, 15): false (Numbers like 14, 14.03, 14.17 in [13, 15) are not being tracked)
 * queryRange(16, 17): true (The number 16 in [16, 17) is still being tracked, despite the remove operation)
 *
 * Note:
 *
 * A half open interval [left, right) denotes all real numbers left <= x < right.
 * 0 < left < right < 10^9 in all calls to addRange, queryRange, removeRange.
 * The total number of calls to addRange in a single test case is at most 1000.
 * The total number of calls to queryRange in a single test case is at most 5000.
 * The total number of calls to removeRange in a single test case is at most 1000.
 */

import java.util.*;

public class RangeModule {
    /**
     * Solution 1: TreeSet with ordering based on left value
     *
     * Use a tree set to store all intervals and make sure none of them overlap by merging/splitting on add/remove.
     * Optimize the for loop when getting the overlapping ranges.
     */
    class RangeModule1 {
        TreeSet<int[]> ranges;

        public RangeModule1() {
            ranges = new TreeSet<>(Comparator.comparingInt(a -> a[0]));
        }

        public void addRange(int left, int right) {
            int min = left, max = right;
            for (int[] range : findOverlap(left, right)) {
                min = Math.min(min, range[0]);
                max = Math.max(max, range[1]);
            }
            ranges.add(new int[]{ min, max });
        }

        public boolean queryRange(int left, int right) {
            int[] target = new int[]{ left, right };
            int[] floor_e = ranges.floor(target);
            return floor_e != null && floor_e[0] <= left && floor_e[1] >= right;
        }

        public void removeRange(int left, int right) {
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for (int[] range : findOverlap(left, right)) {
                min = Math.min(min, range[0]);
                max = Math.max(max, range[1]);
            }
            if (left > min) {
                ranges.add(new int[]{ min, left });
            }
            if (right < max) {
                ranges.add(new int[]{ right, max });
            }
        }

        private List<int[]> findOverlap(int left, int right) {
            int[] target = new int[]{ left, right };
            int[] floor_e = ranges.floor(target);
            Set<int[]> tail_set = floor_e == null ? ranges : ranges.tailSet(floor_e);
            List<int[]> overlap = new ArrayList<>();
            for (Iterator<int[]> iter = tail_set.iterator(); iter.hasNext();) {
                int[] range = iter.next();
                // optimization to break early if no overlap possible
                if (range[0] > right) {
                    break;
                }
                if (isOverlap(target, range)) {
                    iter.remove();
                    overlap.add(range);
                }
            }
            return overlap;
        }

        private boolean isOverlap(int[] a, int[] b) {
            return !(a[1] < b[0] || b[1] < a[0]);
        }
    }

    /**
     * Solution 2: TreeSet with ordering based on right value
     *
     * Slightly improves performance as a floor call can be removed in findOverlap(). Need to use special ranges
     * (starting from zero) for a few places to make sure we get the right ranges.
     */
    class RangeModule2 {
        TreeSet<int[]> ranges;

        public RangeModule2() {
            ranges = new TreeSet<>((a, b) -> {
                if (a[1] == b[1]) {
                    if (a[0] == b[0]) return 0;
                    if (a[0] < b[0]) return -1;
                    return 1;
                }
                return a[1] < b[1] ? -1 : 1;
            });
        }

        public void addRange(int left, int right) {
            int min = left, max = right;
            for (int[] range : findOverlap(left, right)) {
                min = Math.min(min, range[0]);
                max = Math.max(max, range[1]);
            }
            ranges.add(new int[]{ min, max });
        }

        public boolean queryRange(int left, int right) {
            int[] e = ranges.ceiling(new int[]{ 0, right });
            return e != null && e[0] <= left && e[1] >= right;
        }

        public void removeRange(int left, int right) {
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for (int[] range : findOverlap(left, right)) {
                min = Math.min(min, range[0]);
                max = Math.max(max, range[1]);
            }
            if (left > min) {
                ranges.add(new int[]{ min, left });
            }
            if (right < max) {
                ranges.add(new int[]{ right, max });
            }
        }

        private List<int[]> findOverlap(int left, int right) {
            int[] target = new int[]{ left, right };
            Set<int[]> tail_set = ranges.tailSet(new int[]{ 0, left });
            List<int[]> overlap = new ArrayList<>();
            for (Iterator<int[]> iter = tail_set.iterator(); iter.hasNext();) {
                int[] range = iter.next();
                // optimization to break early if no overlap possible
                if (range[0] > right) {
                    break;
                }
                if (isOverlap(target, range)) {
                    iter.remove();
                    overlap.add(range);
                }
            }
            return overlap;
        }

        private boolean isOverlap(int[] a, int[] b) {
            return !(a[1] < b[0] || b[1] < a[0]);
        }
    }
}
