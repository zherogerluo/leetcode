/**
 * LeetCode #1559, hard
 *
 * Given a 2D array of characters grid of size m x n, you need to find if there exists any cycle consisting of the same
 * value in grid.
 *
 * A cycle is a path of length 4 or more in the grid that starts and ends at the same cell. From a given cell, you can
 * move to one of the cells adjacent to it - in one of the four directions (up, down, left, or right), if it has the
 * same value of the current cell.
 *
 * Also, you cannot move to the cell that you visited in your last move. For example, the cycle (1, 1) -> (1, 2) ->
 * (1, 1) is invalid because from (1, 2) we visited (1, 1) which was the last visited cell.
 *
 * Return true if any cycle of the same value exists in grid, otherwise, return false.
 *
 * Example 1:
 *
 * Input: grid = [["a","a","a","a"],["a","b","b","a"],["a","b","b","a"],["a","a","a","a"]]
 * Output: true
 * Explanation: There are two valid cycles shown in different colors in the image below:
 *
 * Example 2:
 *
 * Input: grid = [["c","c","c","a"],["c","d","c","c"],["c","c","e","c"],["f","c","c","c"]]
 * Output: true
 * Explanation: There is only one valid cycle highlighted in the image below:
 *
 * Example 3:
 *
 * Input: grid = [["a","b","b"],["b","z","b"],["b","b","a"]]
 * Output: false
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m <= 500
 * 1 <= n <= 500
 * grid consists only of lowercase English letters.
 */

public class DetectCyclesInTwoDGrid {
    /**
     * Solution 1: Depth-first search
     *
     * Typical DFS, note the way to avoid going back to where it came from.
     */
    class Solution1 {
        static int[][] moves = {{ 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }};

        public boolean containsCycle(char[][] grid) {
            final int m = grid.length, n = grid[0].length;
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (grid[i][j] >= 'a' && grid[i][j] <= 'z' &&
                            solve(grid, grid[i][j], i, j, -1, -1)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean solve(char[][] grid, char c, int i, int j, int iFrom, int jFrom) {
            final int m = grid.length, n = grid[0].length;
            if (i < 0 || i >= m || j < 0 || j >= n) {
                return false;
            }
            if (grid[i][j] == c + 'A' - 'a') {
                return true;
            }
            if (grid[i][j] == c) {
                grid[i][j] = (char)(c + 'A' - 'a');
                boolean res = false;
                for (int[] move : moves) {
                    int iNext = i + move[0], jNext = j + move[1];
                    if (iNext != iFrom || jNext != jFrom) {
                        res |= solve(grid, c, iNext, jNext, i, j);
                    }
                }
                return res;
            }
            return false;
        }
    }
}
