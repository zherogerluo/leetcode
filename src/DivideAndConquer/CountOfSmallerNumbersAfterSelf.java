/**
 * LeetCode #315, hard
 *
 * You are given an integer array nums and you have to return a new counts array. The counts array has the property
 * where counts[i] is the number of smaller elements to the right of nums[i].

 Example:

 Input: [5,2,6,1]
 Output: [2,1,1,0]
 Explanation:
 To the right of 5 there are 2 smaller elements (2 and 1).
 To the right of 2 there is only 1 smaller element (1).
 To the right of 6 there is 1 smaller element (1).
 To the right of 1 there is 0 smaller element.
 */

package DivideAndConquer;

import java.util.*;

public class CountOfSmallerNumbersAfterSelf {
    /**
     * Solution 1: Bubble sort
     *
     * Start from end of array, bubble sort and count swaps. Break if seeing a larger number to optimize performance.
     *
     * Time complexity: O(n^2). Space complexity: O(1) if not counting output.
     */
    class Solution1 {
        public List<Integer> countSmaller(int[] nums) {
            int[] counts = new int[nums.length];
            for (int i = nums.length-2; i >= 0; i--) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[j] < nums[j-1]) {
                        swap(nums, j-1, j);
                        counts[i]++;
                    } else break;
                }
            }
            List<Integer> res = new ArrayList<>();
            for (int count : counts) res.add(count);
            return res;
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }

    /**
     * Solution 2: Merge sort
     *
     * We can use merge sort with some modification: During the merging process, if any number on the right is being
     * swapped to left side, we know that for each number on the left that have not been merged, their counts will
     * increment one. To do this, we sort the indexes instead of numbers, and keep a temporary count during each
     * level of merging. Once right-to-left swap happens, we increment the temporary count; For every left number
     * that is settled, we finalized its count by adding the temporary count to the old count.
     *
     * This method is incredibly clever and guarantees O(n * log(n)) time. However sorting indexes is not easy to
     * write especially with the additional complexity of tracking counts.
     *
     * Time complexity: O(n * log(n)) guaranteed. Space complexity: O(n) for temporary array.
     */
    class Solution2 {
        public List<Integer> countSmaller(int[] nums) {
            int[] indexes = new int[nums.length], counts = new int[nums.length];
            for (int i = 0; i < nums.length; i++) indexes[i] = i;
            mergesort(nums, indexes, 0, nums.length, counts);
            List<Integer> res = new ArrayList<>();
            for (int count : counts) res.add(count);
            return res;
        }

        /* Sort indexes based on values in nums, and increment count if right half is swapped to left */
        private void mergesort(int[] nums, int[] indexes, int start, int end, int[] counts) {
            // end is exclusive
            if (start >= end - 1) return;
            int mid = start + (end - start) / 2;
            mergesort(nums, indexes, start, mid, counts);
            mergesort(nums, indexes, mid, end, counts);
            int[] left = Arrays.copyOfRange(indexes, start, mid);
            int[] right = Arrays.copyOfRange(indexes, mid, end);
            int i = 0, j = 0, count = 0;
            while (i < left.length && j < right.length) {
                if (nums[left[i]] > nums[right[j]]) {
                    // left number is larger than right
                    indexes[start++] = right[j++];
                    count++; // increment temporary count
                } else {
                    // left number is smaller than right
                    counts[left[i]] += count; // finalize count first
                    indexes[start++] = left[i++];
                }
            }
            while (i < left.length) {
                counts[left[i]] += count; // don't forget to update count here
                indexes[start++] = left[i++];
            }
            while (j < left.length) {
                indexes[start++] = right[j++];
            }
        }
    }

    /**
     * Solution 3: Binary search tree
     *
     * A natural idea to improve the O(n^2) solution is to keep a sorted data structure as we go from right to left.
     * A good choice is building a BST where each node contains information about the smaller count. For each node,
     * we keep track of smaller nodes only in this subtree, and also a count of duplicates appeared. Each time we
     * insert a new value, if we go left, the count needs to be incremented, if we go right, we carry current count
     * and duplicates to next level recursively.
     *
     * Tricky part is we need to deal with duplicates explicitly, and return different value for left/right branches
     * (basically carry value if going right but not if going left).
     *
     * Time complexity: O(n * log(n)) average, O(n^2) worst case. Space complexity: O(n).
     */
    class Solution3 {
        class Node {
            int val;
            int count; // number of left branch nodes (smaller than this node as root)
            int dup; // number of duplicate nodes
            Node left, right;

            Node(int val) {
                this.val = val;
                count = 0;
                dup = 1; // self as a duplicate
            }

            /* Returns the number of nodes smaller than val for a tree rooted at this node */
            int insert(int val) {
                if (val > this.val) {
                    if (right == null) {
                        right = new Node(val);
                        return count + dup; // need to include all left branch count and selves
                    } else {
                        return count + dup + right.insert(val); // need to include all left branch count and selves
                    }
                } else if (val < this.val) {
                    count++; // increment count since a new node smaller than this is being added
                    if (left == null) {
                        left = new Node(val);
                        return 0;
                    } else {
                        return left.insert(val);
                    }
                } else {
                    // duplicate node
                    dup++;
                    return count;
                }
            }
        }

        public List<Integer> countSmaller(int[] nums) {
            List<Integer> res = new LinkedList<>();
            if (nums == null || nums.length == 0) return res;
            res.add(0);
            Node root = new Node(nums[nums.length-1]);
            for (int i = nums.length-2; i >= 0; i--) {
                res.add(0, root.insert(nums[i]));
            }
            return res;
        }
    }

    /**
     * Solution 4: Segment tree
     */
    // TODO

    /**
     * Solution 5: Binary indexed tree
     */
    // TODO
}
