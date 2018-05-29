/**
 * LeetCode #4, hard
 *
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.

 Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

 Example 1:
 nums1 = [1, 3]
 nums2 = [2]

 The median is 2.0
 Example 2:
 nums1 = [1, 2]
 nums2 = [3, 4]

 The median is (2 + 3)/2 = 2.5
 */

package Array;

public class MedianOfTwoSortedArrays {
    /**
     * Solution 1: Array partitioning
     *
     * The property of median is that, the numbers of elements to its left and right are equal.
     * We can partition nums1 at location between i-1 and i, splitting the array into two halves,
     * left with i elements and right with m-i elements. From the property of median, we immediately
     * know that nums2 should be partitioned between j-1 and j such that j = (m+n)/2 - i.
     *
     * Another property of median is that, the left side elements are always smaller than right side elements.
     * So we can do a binary search on nums1's partitioning strategy: Partition at center (i = (l + r) / 2),
     * if nums1[i-1] > nums2[j], we should shift the partition to the left; If nums1[i] < nums2[j-1], we should
     * instead shift the partition to the right.
     *
     * Tricky part #1:
     *
     * The total length could be odd or even, and median is defined differently in these two cases.
     * Remember that the final partition (m+n)/2 is always leaning to the left.
     *
     * Tricky part #2:
     *
     * The partition can range from 0 to m, not just m-1. Be sure to check array index bounds very carefully.
     */
    class Solution1 {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            if (nums1 == null || nums2 == null) throw new IllegalArgumentException();
            if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1); // makes sure nums1 is shorter
            final int m = nums1.length, n = nums2.length;
            int l = 0, r = m; // note that r is not m-1
            double res = 0.;
            while (l <= r) {
                int i = (l + r) / 2; // partition at left of nums1[i]
                int j = (m + n) / 2 - i;
                if (i < m && j > 0 && nums1[i] < nums2[j-1]) l = i + 1;
                else if (i > 0 && j < n && nums1[i-1] > nums2[j]) r = i - 1;
                else {
                    int maxL = Integer.MIN_VALUE, minR = Integer.MAX_VALUE;
                    if (i > 0) maxL = Math.max(maxL, nums1[i-1]);
                    if (j > 0) maxL = Math.max(maxL, nums2[j-1]);
                    if (i < m) minR = Math.min(minR, nums1[i]);
                    if (j < n) minR = Math.min(minR, nums2[j]);
                    return (m + n) % 2 == 0 ? (double)(maxL+minR)/2 : minR; // handles odd and even cases
                }
            }
            return res;
        }
    }
}
