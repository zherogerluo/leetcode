/**
 * LeetCode #845, medium
 *
 * You may recall that an array arr is a mountain array if and only if:
 *
 * arr.length >= 3
 * There exists some index i (0-indexed) with 0 < i < arr.length - 1 such that:
 * arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
 * arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
 * Given an integer array arr, return the length of the longest subarray, which is a mountain. Return 0 if there is no
 * mountain subarray.
 *
 * Example 1:
 *
 * Input: arr = [2,1,4,7,3,2,5]
 * Output: 5
 * Explanation: The largest mountain is [1,4,7,3,2] which has length 5.
 *
 * Example 2:
 *
 * Input: arr = [2,2,2]
 * Output: 0
 * Explanation: There is no mountain.
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^4
 * 0 <= arr[i] <= 10^4
 *
 * Follow up:
 *
 * Can you solve it using only one pass?
 * Can you solve it in O(1) space?
 */

public class LongestMountainInArray {
    /**
     * Solution 1:
     *
     * One pass, check for up/down/flat hill and do various stuff. Very hard to get it right due to corner cases.
     */
    class Solution1 {
        public int longestMountain(int[] arr) {
            if (arr.length < 3) {
                return 0;
            }
            int left = Integer.MAX_VALUE, max = 0;
            for (int i = 1; i < arr.length; ++i) {
                if (arr[i-1] < arr[i]) {
                    // beware of the left edge corner case i == 1
                    if (i == 1 || arr[i-2] >= arr[i-1]) {
                        left = i - 1;
                    }
                } else if (arr[i-1] > arr[i]) {
                    // beware of the right edge corner case
                    if (i == arr.length - 1 || arr[i] <= arr[i+1]) {
                        max = Math.max(max, i - left + 1);
                        left = i;
                    }
                } else {
                    // flat, need to invalidate left to take care of example [2, 2, 1]
                    left = Integer.MAX_VALUE;
                }
            }
            return max;
        }
    }

    /**
     * Solution 2:
     *
     * Technically this is a two-pass solution but the idea is more straightforward and the implementation is much less
     * error-prone. We find the peak first, then expand it to reach the left and right feet and calculate the length.
     */
    class Solution2 {
        public int longestMountain(int[] arr) {
            if (arr.length < 3) {
                return 0;
            }
            int left = Integer.MAX_VALUE, max = 0;
            for (int i = 1; i < arr.length - 1; ++i) {
                if (arr[i-1] < arr[i] && arr[i] > arr[i+1]) {
                    // peak found, expand to get the mountain length
                    int l = i - 1, r = i + 1;
                    while (l > 0 && arr[l-1] < arr[l]) {
                        --l;
                    }
                    while (r < arr.length - 1 && arr[r] > arr[r+1]) {
                        ++r;
                    }
                    max = Math.max(max, r - l + 1);
                }
            }
            return max;
        }
    }
}
