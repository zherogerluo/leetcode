/**
 * LeetCode #657, easy
 *
 * Initially, there is a Robot at position (0, 0). Given a sequence of its moves, judge if this robot makes a circle,
 * which means it moves back to the original place.
 *
 * The move sequence is represented by a string. And each move is represent by a character. The valid robot moves are
 * R (Right), L (Left), U (Up) and D (down). The output should be true or false representing whether the robot makes
 * a circle.
 *
 * Example 1:
 *
 * Input: "UD"
 * Output: true
 *
 *
 * Example 2:
 *
 * Input: "LL"
 * Output: false
 */

package String;

public class JudgeRouteCircle {
    /**
     * Solution 1:
     *
     * Basically a brain teaser. The robot returns to original position if and only if the number of up moves
     * equals that of down moves and same for left and right moves.
     */
    class Solution1 {
        public boolean judgeCircle(String moves) {
            int u = 0, l = 0;
            for (char c : moves.toCharArray()) {
                if (c == 'U') u++;
                else if (c == 'D') u--;
                else if (c == 'L') l++;
                else if (c == 'R') l--;
                else return false;
            }
            return u == 0 && l == 0;
        }
    }
}
