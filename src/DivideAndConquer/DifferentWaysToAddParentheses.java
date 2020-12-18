/**
 * LeetCode #241, medium
 *
 * Given a string of numbers and operators, return all possible results from computing all the different possible ways
 * to group numbers and operators. The valid operators are +, - and *.
 *
 * Example 1:
 *
 * Input: "2-1-1"
 * Output: [0, 2]
 * Explanation:
 * ((2-1)-1) = 0
 * (2-(1-1)) = 2
 *
 * Example 2:
 *
 * Input: "2*3-4*5"
 * Output: [-34, -14, -10, -10, 10]
 * Explanation:
 * (2*(3-(4*5))) = -34
 * ((2*3)-(4*5)) = -14
 * ((2*(3-4))*5) = -10
 * (2*((3-4)*5)) = -10
 * (((2*3)-4)*5) = 10
 */

import java.util.*;

public class DifferentWaysToAddParentheses {
    /**
     * Solution 1: Recursion with memoization
     *
     * Split the string by operator chars and recursively calculate results. Straightforward idea. Use a hashmap to
     * memorize sub-problem results.
     */
    class Solution1 {
        public List<Integer> diffWaysToCompute(String input) {
            return helper(input, new HashMap<>());
        }

        private List<Integer> helper(String input, Map<String, List<Integer>> memo) {
            if (memo.containsKey(input)) {
                return memo.get(input);
            }
            List<Integer> res = new ArrayList<>();
            char[] chars = input.toCharArray();
            for (int i = 0; i < chars.length; ++i) {
                if (chars[i] == '-' || chars[i] == '+' || chars[i] == '*') {
                    List<Integer> pre = helper(input.substring(0, i), memo);
                    List<Integer> post = helper(input.substring(i+1, chars.length), memo);
                    for (int pre_num : pre) {
                        for (int post_num : post) {
                            switch (chars[i]) {
                                case '-':
                                    res.add(pre_num - post_num);
                                    break;
                                case '+':
                                    res.add(pre_num + post_num);
                                    break;
                                case '*':
                                    res.add(pre_num * post_num);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }
            memo.put(input, res);
            if (res.isEmpty()) {
                res.add(Integer.valueOf(input));
            }
            return res;
        }
    }
}
