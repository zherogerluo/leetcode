/**
 * LeetCode #295, hard
 *
 * Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value.
 * So the median is the mean of the two middle value.

 For example,
 [2,3,4], the median is 3

 [2,3], the median is (2 + 3) / 2 = 2.5

 Design a data structure that supports the following two operations:

 void addNum(int num) - Add a integer number from the data stream to the data structure.
 double findMedian() - Return the median of all elements so far.
 Example:

 addNum(1)
 addNum(2)
 findMedian() -> 1.5
 addNum(3)
 findMedian() -> 2
 */

package Heap;

import java.util.*;

public class FindMedianFromDataStream {
    /**
     * Solution 1: Heap
     *
     * Two heaps storing smaller and larger halves, respectively. Note we need to add number to the right half, and
     * make sure the size difference is at most one.
     *
     * Time complexity: O(log(n)) for add, O(1) for find. Space complexity: O(n).
     */
    class MedianFinder {
        Queue<Integer> small, large;

        /** initialize your data structure here. */
        public MedianFinder() {
            small = new PriorityQueue<>(Comparator.reverseOrder());
            large = new PriorityQueue<>();
        }

        public void addNum(int num) {
            if (small.size() == 0 || num <= small.peek()) small.offer(num);
            else large.offer(num);
            while (small.size() > large.size() + 1) large.offer(small.poll());
            while (large.size() > small.size()) small.offer(large.poll());
        }

        public double findMedian() {
            if (small.size() == 0) return 0;
            if (small.size() == large.size()) return (double) (small.peek() + large.peek()) / 2.;
            return small.peek();
        }
    }
}
