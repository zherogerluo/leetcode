/**
 * LeetCode #1575, hard
 *
 * You are given an array of distinct positive integers locations where locations[i] represents the position of city i.
 * You are also given integers start, finish and fuel representing the starting city, ending city, and the initial
 * amount of fuel you have, respectively.
 *
 * At each step, if you are at city i, you can pick any city j such that j != i and 0 <= j < locations.length and move
 * to city j. Moving from city i to city j reduces the amount of fuel you have by |locations[i] - locations[j]|. Please
 * notice that |x| denotes the absolute value of x.
 *
 * Notice that fuel cannot become negative at any point in time, and that you are allowed to visit any city more than
 * once (including start and finish).
 *
 * Return the count of all possible routes from start to finish.
 *
 * Since the answer may be too large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: locations = [2,3,6,8,4], start = 1, finish = 3, fuel = 5
 * Output: 4
 * Explanation: The following are all possible routes, each uses 5 units of fuel:
 * 1 -> 3
 * 1 -> 2 -> 3
 * 1 -> 4 -> 3
 * 1 -> 4 -> 2 -> 3
 *
 * Example 2:
 *
 * Input: locations = [4,3,1], start = 1, finish = 0, fuel = 6
 * Output: 5
 * Explanation: The following are all possible routes:
 * 1 -> 0, used fuel = 1
 * 1 -> 2 -> 0, used fuel = 5
 * 1 -> 2 -> 1 -> 0, used fuel = 5
 * 1 -> 0 -> 1 -> 0, used fuel = 3
 * 1 -> 0 -> 1 -> 0 -> 1 -> 0, used fuel = 5
 *
 * Example 3:
 *
 * Input: locations = [5,2,1], start = 0, finish = 2, fuel = 3
 * Output: 0
 * Explanation: It's impossible to get from 0 to 2 using only 3 units of fuel since the shortest route needs 4 units of
 * fuel.
 *
 * Example 4:
 *
 * Input: locations = [2,1,5], start = 0, finish = 0, fuel = 3
 * Output: 2
 * Explanation: There are two possible routes, 0 and 0 -> 1 -> 0.
 *
 * Example 5:
 *
 * Input: locations = [1,2,3], start = 0, finish = 2, fuel = 40
 * Output: 615088286
 * Explanation: The total number of possible routes is 2615088300. Taking this number modulo 10^9 + 7 gives us
 * 615088286.
 *
 * Constraints:
 *
 * 2 <= locations.length <= 100
 * 1 <= locations[i] <= 10^9
 * All integers in locations are distinct.
 * 0 <= start, finish < locations.length
 * 1 <= fuel <= 200
 */

package DynamicProgramming;

import java.util.*;

public class CountAllPossibleRoutes {
    /**
     * Solution 1: Depth-first search with memoization
     *
     * Typical DFS, not a hard problem.
     *
     * Time complexity: O(m * n). Space complexity: O(m * n).
     */
    class Solution1 {
        public int countRoutes(int[] locations, int start, int finish, int fuel) {
            return helper(locations, start, finish, fuel, new int[locations.length][fuel+1]);
        }

        private int helper(int[] locations, int start, int finish, int fuel, int[][] memo) {
            if (fuel < Math.abs(locations[start] - locations[finish])) {
                return 0;
            }
            if (memo[start][fuel] != 0) {
                return memo[start][fuel] - 1;
            }
            final int MOD = 1000000007;
            int res = start == finish ? 1 : 0;
            for (int i = 0; i < locations.length; ++i) {
                if (start == i) {
                    continue;
                }
                res += helper(locations, i, finish, fuel - Math.abs(locations[i] - locations[start]), memo);
                res %= MOD;
            }
            memo[start][fuel] = res + 1;
            return res;
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * Bottom-up DP. Note this is slower than Solution 1 because it calculates all cases and some of them might never
     * get used as possible fuel left is most likely discrete. There should be a way to optimize this, for example,
     * calculate from fuel to fuel-cost, and update fuel-cost.
     */
    class Solution2 {
        public int countRoutes(int[] locations, int start, int finish, int fuel) {
            // dp[start][fuel] = sum(dp[next][fuel-cost] for next not start in locations)
            final int MOD = 1000000007;
            int[][] dp = new int[locations.length][fuel+1];
            Arrays.fill(dp[finish], 1);
            for (int j = 0; j <= fuel; ++j) {
                for (int i = 0; i < locations.length; ++i) {
                    for (int ii = 0; ii < locations.length; ++ii) {
                        if (i == ii) {
                            continue;
                        }
                        int cost = Math.abs(locations[ii] - locations[i]);
                        if (cost <= j) {
                            dp[i][j] = (dp[i][j] + dp[ii][j-cost]) % MOD;
                        }
                    }
                }
            }
            return dp[start][fuel];
        }
    }
}
