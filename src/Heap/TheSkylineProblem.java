/**
 * LeetCode #218, hard
 *
 * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from
 * a distance. Now suppose you are given the locations and height of all the buildings as shown on a cityscape
 * photo (Figure A), write a program to output the skyline formed by these buildings collectively (Figure B).

 The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi], where Li and Ri are
 the x coordinates of the left and right edge of the ith building, respectively, and Hi is its height. It is
 guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0. You may assume all buildings are perfect
 rectangles grounded on an absolutely flat surface at height 0.

 For instance, the dimensions of all buildings in Figure A are recorded as: [ [2 9 10], [3 7 15], [5 12 12],
 [15 20 10], [19 24 8] ] .

 The output is a list of "key points" (red dots in Figure B) in the format of [ [x1,y1], [x2, y2], [x3, y3], ... ]
 that uniquely defines a skyline. A key point is the left endpoint of a horizontal line segment. Note that the last
 key point, where the rightmost building ends, is merely used to mark the termination of the skyline, and always has
 zero height. Also, the ground in between any two adjacent buildings should be considered part of the skyline contour.

 For instance, the skyline in Figure B should be represented as:[ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8],
 [24, 0] ].

 Notes:

 The number of buildings in any input list is guaranteed to be in the range [0, 10000].
 The input list is already sorted in ascending order by the left x position Li.
 The output list must be sorted by the x position.
 There must be no consecutive horizontal lines of equal height in the output skyline. For instance, [...[2 3], [4 5],
 [7 5], [11 5], [12 7]...] is not acceptable; the three lines of height 5 should be merged into one in the final
 output as such: [...[2 3], [4 5], [12 7], ...]
 */

package Heap;

import java.util.*;

public class TheSkylineProblem {
    /**
     * Solution 1: Heap
     *
     * The key idea to solve the problem is that, for every candidate point, the height should be that of the highest
     * building seen so far. We convert buildings to key points, each point stores its x and building height. Sort
     * key points by its x, and iterate through all key points and add the height to max heap. We add new key point
     * to results for current x at height of max in heap.
     *
     * Trick #1: We need to remove height if current point is end of a building. A trick to do this is to assign a
     * negative height to that point, so that we know it is an end point by checking its sign. However, removing a
     * value from heap takes O(n * log(n)) (e.g. removing the smallest) and therefore this solution is NOT optimal.
     *
     * Trick #2: How do we deal with cases that multiple key points lie at same x? One way is, when sorting them, put
     * those with higher height in the front, so that the highest height at that x is always added to heap first.
     * This also ensures that new height is added first if a starting and an ending points overlaps.
     *
     * Time complexity: O(n^2 * log(n)): n loops, in each loop, searching and removing a height from heap can take O
     * (n * log(n)).
     *
     * Space complexity: O(n).
     */
    class Solution1 {
        public List<int[]> getSkyline(int[][] buildings) {
            List<int[]> keypoints = new ArrayList<>();
            for (int[] b : buildings) {
                keypoints.add(new int[]{b[0], b[2]});
                keypoints.add(new int[]{b[1], -b[2]}); // add negative height to mark as an ending point for a building
            }
            // sort keypoints based on x, if equal, higher point or start point comes first
            keypoints.sort(new Comparator<int[]>() {
                public int compare(int[] a, int[] b) {
                    return (a[0] - b[0]) == 0 ? (b[1] - a[1]) : (a[0] - b[0]);
                }
            });
            Queue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());
            queue.offer(0); // either add a dummy zero here, or do empty check in later loop
            List<int[]> res = new ArrayList<>();
            int lastX = -1, lastH = 0;
            for (int[] point : keypoints) {
                int x = point[0], h = point[1];
                // add or remove height from heap
                if (h > 0) queue.offer(h);
                else queue.remove(-h); // this can take O(n * log(n))
                // add key point
                h = queue.peek();
                if (x != lastX && h != lastH) {
                    res.add(new int[]{x, h});
                    lastX = x;
                    lastH = h;
                }
            }
            return res;
        }
    }

    /**
     * Solution 2: Heap
     *
     * The caveat of Solution 1 is that, removing a height from heap takes O(n). We don't have to do that if we know
     * exactly what height (or range of heights) we want to remove. The idea of this solution is to put buildings in
     * the heap instead of heights, and add a building index to key point that denotes which building it belongs to.
     * For a new key point, we remove only the buildings in front of it, from top of heap. Removing single one will
     * only take O(log(n)), much better than the O(n * log(n)) single height removal from Solution 1.
     *
     * Trick #1: For an ending point of the building, we need to assign a height of zero, to ensure that when it
     * overlaps with another building's starting point, the starting point goes to front after sorting. Otherwise the
     * wrong point [end, height] would go into result.
     *
     * Time complexity: O(n * log(n)): n loops, soring takes n * log(n), and removing from heap is done for at
     * most n times in total which takes n * log(n).
     *
     * Space complexity: O(n).
     */
    class Solution2 {
        public List<int[]> getSkyline(int[][] buildings) {
            List<int[]> keypoints = new ArrayList<>();
            for (int i = 0; i < buildings.length; i++) {
                int[] b = buildings[i];
                keypoints.add(new int[]{b[0], b[2], i}); // {x, height, building index} - height is just for sorting
                keypoints.add(new int[]{b[1], 0, i}); // note the end point must be assigned height of zero
            }
            // sort keypoints based on x, if equal, higher point or start point comes first
            keypoints.sort(new Comparator<int[]>() {
                public int compare(int[] a, int[] b) {
                    return (a[0] - b[0]) == 0 ? (b[1] - a[1]) : (a[0] - b[0]);
                }
            });
            // priority queue for buildings
            Queue<int[]> queue = new PriorityQueue<>(new Comparator<int[]> () {
                public int compare(int[] a, int[] b) {
                    return b[2] - a[2]; // highest building comes first
                }
            });
            List<int[]> res = new ArrayList<>();
            int lastX = -1, lastH = 0;
            for (int[] point : keypoints) {
                int x = point[0], i = point[2];
                // remove all buildings in front of this point
                while (!queue.isEmpty() && queue.peek()[1] <= x) queue.poll(); // note the <= sign
                // if it is a starting point, insert its building to heap
                if (isStart(point, buildings[i])) queue.offer(buildings[i]);
                // add key point
                int h = queue.isEmpty() ? 0 : queue.peek()[2];
                if (x != lastX && h != lastH) {
                    res.add(new int[]{x, h});
                    lastX = x;
                    lastH = h;
                }
            }
            return res;
        }

        /* Returns true if the key point is the start point of the building. */
        private boolean isStart(int[] point, int[] building) {
            return point[0] == building[0];
        }
    }

    /**
     * Solution 3: Merge sort
     *
     * A single building can be converted to a skyline with two key points. If we can merge arbitrary two skylines,
     * we can solve this problem like merge sort. The merge method is like merging two linked list except we keep
     * track heights from each list and add node to result based on the higher of the two.
     *
     * Tricky part is how to deal with overlapping points in x or h. See the comment in the code.
     *
     * Time complexity: O(n * log(n)). Space complexity: O(n).
     */
    class Solution3 {
        public List<int[]> getSkyline(int[][] buildings) {
            return new ArrayList<>(merge(buildings, 0, buildings.length-1));
        }

        private Queue<int[]> merge(int[][] buildings, int start, int end) {
            Queue<int[]> res = new LinkedList<>(); // use queue for easy iteration with peek
            if (start > end) return res;
            if (start == end) {
                int[] bldg = buildings[start];
                res.offer(new int[]{bldg[0], bldg[2]});
                res.offer(new int[]{bldg[1], 0});
                return res;
            }
            int mid = start + (end - start) / 2;
            Queue<int[]> l1 = merge(buildings, start, mid);
            Queue<int[]> l2 = merge(buildings, mid+1, end);
            // merge two skylines
            int x = 0, h1 = 0, h2 = 0, max = 0; // these need to go outside the while loop!
            while (!l1.isEmpty() && !l2.isEmpty()) {
                int[] head1 = l1.peek(), head2 = l2.peek();
                if (head1[0] <= head2[0]) {
                    x = head1[0];
                    h1 = head1[1];
                    l1.poll();
                }
                // separate loop with overlapping condition statement
                // deals with overlapping x
                // alternatively we can treat head1[0] == head2[0] explicitly in an else clause
                if (head1[0] >= head2[0]) {
                    x = head2[0];
                    h2 = head2[1];
                    l2.poll();
                }
                int curMax = Math.max(h1, h2);
                if (curMax != max) { // deal with overlapping h
                    res.offer(new int[]{x, curMax});
                    max = curMax;
                }
            }
            // don't forget to add remaining points
            for (int[] p : l1) res.offer(p);
            for (int[] p : l2) res.offer(p);
            return res;
        }
    }
}
