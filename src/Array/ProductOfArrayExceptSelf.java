/**
 * LeetCode #238, medium
 *
 * Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to the product
 * of all the elements of nums except nums[i].

 Example:

 Input:  [1,2,3,4]
 Output: [24,12,8,6]
 Note: Please solve it without division and in O(n).

 Follow up:
 Could you solve it with constant space complexity? (The output array does not count as extra space for the purpose
 of space complexity analysis.)
 */

package Array;

import java.util.*;

public class ProductOfArrayExceptSelf {
    /**
     * Solution 1:
     *
     * Use two arrays to store the left product and right product separately, then the final result res[i] = left[i]
     * * right[i].
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution1 {
        public int[] productOfArrayExceptSelf(int[] nums) {
            int[] left = new int[nums.length];
            int[] right = new int[nums.length];
            Arrays.fill(left, 1);
            Arrays.fill(right, 1);
            for (int i = 1, j = nums.length-2; i < nums.length && j >= 0; i++, j--) {
                left[i] = left[i-1] * nums[i-1];
                right[j] = right[j+1] * nums[j+1];
            }
            for (int i = 0; i < nums.length; i++) {
                left[i] *= right[i];
            }
            return left;
        }
    }

    /**
     * Solution 2:
     *
     * We don't actually have to store both left and right product. We can just store left product, and calculate
     * right product while we iterate through the array and update the left product array to be the final results.
     *
     * Time complexity: O(n). Space complexity: O(1) if not counting result array.
     */
    class Solution2 {
        public int[] productOfArrayExceptSelf(int[] nums) {
            int[] res = new int[nums.length];
            Arrays.fill(res, 1);
            for (int i = 1; i < nums.length; i++) {
                res[i] = res[i-1] * nums[i-1];
            }
            int rightProduct = 1;
            for (int i = nums.length-2; i >= 0; i--) {
                rightProduct *= nums[i+1];
                res[i] *= rightProduct;
            }
            return res;
        }
    }

    public void test() {
        int[] nums = {1, 2, 3, 4};
        System.out.println(Arrays.toString(new Solution1().productOfArrayExceptSelf(nums)));
        System.out.println(Arrays.toString(new Solution2().productOfArrayExceptSelf(nums)));
    }

    public static void main(String[] args) {
        new ProductOfArrayExceptSelf().test();
    }
}
