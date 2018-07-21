/**
 * LeetCode #287, medium
 *
 * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at
 * least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.

 Example 1:

 Input: [1,3,4,2,2]
 Output: 2
 Example 2:

 Input: [3,1,3,4,2]
 Output: 3
 Note:

 You must not modify the array (assume the array is read only).
 You must use only constant, O(1) extra space.
 Your runtime complexity should be less than O(n2).
 There is only one duplicate number in the array, but it could be repeated more than once.
 */

package Array;

public class FindTheDuplicateNumber {
    /**
     * Solution 1: Bucket sort
     *
     * Bucket sort the numbers, if seen already there, return it as result.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution1 {
        public int findDuplicate(int[] nums) {
            int[] record = new int[nums.length];
            for (int num : nums) {
                if (record[num] != 0) return num;
                record[num] = num;
            }
            return -1;
        }
    }

    /**
     * Solution 2: Binary search
     *
     * The duplicate number is between 1 and n, cut this range by half each time: Loop through entire array every
     * time to count numbers that are smaller than mid, if this count is larger than mid, then the duplicate number
     * must be between 1 and mid. This algorithm relies on the assumption that only one number is duplicated.
     *
     * Time complexity: O(n * log(n)). Space complexity: O(1).
     */
    class Solution2 {
        public int findDuplicate(int[] nums) {
            final int n = nums.length - 1;
            int low = 1, high = n, mid;
            while (low < high) {
                mid = (low + high) / 2;
                int count = 0;
                for (int i = 0; i <= n; i++) {
                    if (nums[i] <= mid) count++;
                }
                if (count > mid) high = mid; // this is more straightforward than low <= high and high = mid - 1
                else low = mid + 1;
            }
            return low;
        }
    }

    /**
     * Solution 3: Two pointers
     *
     * This is linked list cycle detection, with index being the node and value being the pointer to next. The
     * duplicate number is the entry point of the cycle. Use slow and fast (2x slow) pointers. When they meet, start
     * a new pointer from head, then they must meet at where slow and fast used to meet because fast had traveled
     * twice distance of slow and now slow will cover the path of fast as the new pointer travels. They must meet in
     * the cycle and they travel at same speed, so the first time they meet will be the entry point of the cycle,
     * hence the duplicate number.
     *
     * jun1013:
     *
     * "Think of a linked list with a cycle in it somewhere. Say fast pointer goes twice as fast as the slow pointer,
     * and when they meet at point A, they both must be within the cycle. Now consider this, how much further has the
     * fast pointer gone than the slow pointer has? Some multiplier of the loop length, which is exactly how much the
     * slow pointer has gone before fast ans slow meet. (Proof: Time of travel "t" for both fast and slow are the
     * same, Dist(fast) = v(fast) * t = 2 * v(slow) * t, so Dist(fast) - Dist(slow) = 2 * v(slow) * t - v(slow) * t =
     * v(slow) * t = Dist(slow)). This means, the distant from starting point to point A must be the n length of the
     * loop length. So when we set another point starting from the start point and its speed is same as the slow
     * pointer, if they walk a distance of n length of the loop, they will be at point A at the same time, so they
     * must enter the loop at the same time. That's why the point where they first meet is the entry point of the loop."
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution3 {
        public int findDuplicate(int[] nums) {
            int slow = nums[0], fast = nums[nums[0]]; // note the initialization here
            while (slow != fast) {
                slow = nums[slow];
                fast = nums[nums[fast]];
            }
            int slow2 = 0;
            while (slow != slow2) {
                slow = nums[slow];
                slow2 = nums[slow2];
            }
            return slow;
        }
    }
}
