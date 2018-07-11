/**
 * LeetCode #215, medium
 *
 * Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order,
 * not the kth distinct element.

 Example 1:

 Input: [3,2,1,5,6,4] and k = 2
 Output: 5
 Example 2:

 Input: [3,2,3,1,2,4,5,5,6] and k = 4
 Output: 4
 Note:
 You may assume k is always valid, 1 ≤ k ≤ array's length.
 */

package Heap;

import java.util.*;

public class KthLargestElementInAnArray {
    /**
     * Solution 1: Heap
     *
     * Maintain a heap, when its size exceeds k, poll an element (current smallest) out.
     *
     * Time complexity: O(n * log(k)). Space complexity: O(k).
     */
    class Solution1 {
        public int findKthLargest(int[] nums, int k) {
            Queue<Integer> queue = new PriorityQueue<>();
            for (int num : nums) {
                queue.offer(num);
                if (queue.size() > k) queue.poll();
            }
            return queue.poll();
        }
    }

    /**
     * Solution 2: Quick select
     *
     * We can borrow the idea of quick sort to partially sort the array until we find the kth largest element. We
     * partition the array and find the right index of the pivot. If index is too small, then we partition on the
     * right side, otherwise partition on the left side, until the index == nums.length - k.
     *
     * Just like quick sort, we can shuffle the array to improve performance.
     *
     * Time complexity: O(n) on average, O(n^2) worst case. Space complexity: O(1).
     */
    class Solution2 {
        public int findKthLargest(int[] nums, int k) {
            shuffle(nums);
            k = nums.length - k;
            int left = 0, right = nums.length - 1;
            while (left <= right) { // note the condition here
                int pivot = partition(nums, left, right);
                if (pivot == k) return nums[pivot];
                if (pivot < k) left = pivot + 1;
                else right = pivot - 1;
            }
            return -1;
        }

        /* Partition the array so that all numbers to the left of pivot are smaller, and return the pivot index. */
        private int partition(int[] nums, int left, int right) {
            int pivot = left;
            left++;
            while (left <= right) { // note the condition here
                if (nums[left] < nums[pivot]) left++;
                else if (nums[right] > nums[pivot]) right--;
                else swap(nums, left++, right--);
            }
            swap(nums, pivot, right); // swap pivot element to right index (which is smaller than pivot)
            return right;
        }

        private void shuffle(int[] nums) {
            Random rand = new Random();
            for (int i = 0; i < nums.length; i++) {
                swap(nums, i, rand.nextInt(nums.length));
            }
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
