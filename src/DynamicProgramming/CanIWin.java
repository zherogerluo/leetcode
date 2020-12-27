/**
 * LeetCode #464, medium
 *
 * In the "100 game" two players take turns adding, to a running total, any integer from 1 to 10. The player who first
 * causes the running total to reach or exceed 100 wins.
 *
 * What if we change the game so that players cannot re-use integers?
 *
 * For example, two players might take turns drawing from a common pool of numbers from 1 to 15 without replacement
 * until they reach a total >= 100.
 *
 * Given two integers maxChoosableInteger and desiredTotal, return true if the first player to move can force a win,
 * otherwise return false. Assume both players play optimally.
 *
 * Example 1:
 *
 * Input: maxChoosableInteger = 10, desiredTotal = 11
 * Output: false
 * Explanation:
 * No matter which integer the first player choose, the first player will lose.
 * The first player can choose an integer from 1 up to 10.
 * If the first player choose 1, the second player can only choose integers from 2 up to 10.
 * The second player will win by choosing 10 and get a total = 11, which is >= desiredTotal.
 * Same with other integers chosen by the first player, the second player will always win.
 *
 * Example 2:
 *
 * Input: maxChoosableInteger = 10, desiredTotal = 0
 * Output: true
 *
 * Example 3:
 *
 * Input: maxChoosableInteger = 10, desiredTotal = 1
 * Output: true
 *
 * Constraints:
 *
 * 1 <= maxChoosableInteger <= 20
 * 0 <= desiredTotal <= 300
 */

package DynamicProgramming;

public class CanIWin {
    /**
     * Solution 1: DFS with memoization
     *
     * The "used" data structure can uniquely defines the state of the game, so we have to use it as the key to the
     * memoization. Conventionally we use boolean array for "used", but since maxChoosableInteger is less than 20, we
     * can use integer as a bit mask to represent it. This reduces time complexity from O(n!) to O(2^n).
     *
     * Also note the corner cases.
     */
    class Solution1 {
        public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
            if (desiredTotal <= maxChoosableInteger) {
                return true;
            }
            if (desiredTotal > (1+maxChoosableInteger)*maxChoosableInteger/2) {
                return false;
            }
            return play(maxChoosableInteger, 0, 0, desiredTotal, new int[(1<<(maxChoosableInteger))]);
        }

        // returns true iff the first player can force a win
        private boolean play(int max_allowed, int used, int player, int remaining, int[] memo) {
            if (memo[used] != 0) {
                return memo[used] == 1;
            }
            boolean first_player_always_win = true;
            for (int i = max_allowed; i >= 1; --i) {
                int mask = 1 << (i-1);
                if ((used & mask) != 0) {
                    continue;
                }
                if (i >= remaining) {
                    return player == 0;
                }
                boolean first_player_won = play(max_allowed, used | mask, 1-player, remaining-i, memo);
                if (player == 0 && first_player_won) {
                    memo[used] = 1;
                    return true;
                }
                if (player == 1 && !first_player_won) {
                    memo[used] = -1;
                    return false;
                }
                first_player_always_win &= first_player_won;
            }
            memo[used] = first_player_always_win ? 1 : -1;
            return first_player_always_win;
        }
    }

    /**
     * Solution 2:
     *
     * We don't have to id the player because the "used" state will implicitly tell us who is playing now. The helper
     * method returns true if current player can win.
     */
    class Solution2 {
        public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
            if (desiredTotal <= maxChoosableInteger) {
                return true;
            }
            if (desiredTotal > (1+maxChoosableInteger)*maxChoosableInteger/2) {
                return false;
            }
            return play(maxChoosableInteger, 0, desiredTotal, new int[(1<<(maxChoosableInteger))]);
        }

        // returns true iff current player can force a win
        private boolean play(int max_allowed, int used, int remaining, int[] memo) {
            if (memo[used] != 0) {
                return memo[used] == 1;
            }
            for (int i = max_allowed; i >= 1; --i) {
                int mask = 1 << (i-1);
                if ((used & mask) != 0) {
                    continue;
                }
                if (i >= remaining) {
                    return true;
                }
                boolean other_player_can_win = play(max_allowed, used | mask, remaining-i, memo);
                if (!other_player_can_win) {
                    memo[used] = 1;
                    return true;
                }
            }
            memo[used] = -1;
            return false;
        }
    }
}
