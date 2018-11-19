/**
 * LeetCode #289, medium
 *
 * According to the Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton
 * devised by the British mathematician John Horton Conway in 1970."
 *
 * Given a board with m by n cells, each cell has an initial state live (1) or dead (0). Each cell interacts with its
 * eight neighbors (horizontal, vertical, diagonal) using the following four rules (taken from the above Wikipedia
 * article):
 *
 * Any live cell with fewer than two live neighbors dies, as if caused by under-population.
 * Any live cell with two or three live neighbors lives on to the next generation.
 * Any live cell with more than three live neighbors dies, as if by over-population..
 * Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 *
 * Write a function to compute the next state (after one update) of the board given its current state. The next state
 * is created by applying the above rules simultaneously to every cell in the current state, where births and deaths
 * occur simultaneously.
 *
 * Example:
 *
 * Input:
 * [
 *   [0,1,0],
 *   [0,0,1],
 *   [1,1,1],
 *   [0,0,0]
 * ]
 * Output:
 * [
 *   [0,0,0],
 *   [1,0,1],
 *   [0,1,1],
 *   [0,1,0]
 * ]
 *
 * Follow up:
 *
 * Could you solve it in-place? Remember that the board needs to be updated at the same time: You cannot update some
 * cells first and then use their updated values to update other cells.
 *
 * In this question, we represent the board using a 2D array. In principle, the board is infinite, which would cause
 * problems when the active area encroaches the border of the array. How would you address these problems?
 */

package Array;

public class GameOfLife {
    /**
     * Solution 1: Special state
     *
     * To do it in-place, we need to store more information in the cell. In this solution we choose -1 to represent
     * "dead in current state but live in next", and 2 to represent "live in current state but dead in next". This
     * way, to calculate live neighbors, one could simply count neighbors that are positive. After all updates are
     * done, convert state 2 to 0 and -1 to 1.
     *
     * Time complexity: O(m * n). Space complexity: O(1).
     */
    class Solution1 {
        public void gameOfLife(int[][] board) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    // use delta i and j for simplicity
                    int count = -board[i][j]; // offset self
                    for (int di = -1; di <= 1; di++) {
                        for (int dj = -1; dj <= 1; dj++) {
                            count += isLive(board, i + di, j + dj);
                        }
                    }
                    int next;
                    if (count < 2 || count > 3) next = 0;
                    else if (board[i][j] == 0 && count == 2) next = 0;
                    else next = 1;
                    if (board[i][j] != next) board[i][j] = board[i][j] == 1 ? 2 : -1;
                }
            }
            // convert temporary states to valid states
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == 2) board[i][j] = 0;
                    else if (board[i][j] == -1) board[i][j] = 1;
                }
            }
        }

        private int isLive(int[][] board, int i, int j) {
            if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) return 0;
            return board[i][j] > 0 ? 1 : 0; // state 1 and 2 are all currently live
        }
    }

    /**
     * Solution 2: Bit manipulation
     *
     * Instead of defining new states as new numbers, we can simply put next state in the second last bit since current
     * state 0 or 1 only utilizes the last bit:
     *
     * 000...00 = currently dead, dead next
     * 000...01 = currently live, dead next
     * 000...10 = currently dead, live next
     * 000...11 = currently live, live next
     * 
     * More elegant and actually easier to understand.
     *
     * Time complexity: O(m * n). Space complexity: O(1).
     */
    class Solution2 {
        public void gameOfLife(int[][] board) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    // use delta i and j for simplicity
                    int count = -board[i][j]; // offset self
                    for (int di = -1; di <= 1; di++) {
                        for (int dj = -1; dj <= 1; dj++) {
                            count += isLive(board, i + di, j + dj);
                        }
                    }
                    int next;
                    if (count < 2 || count > 3) next = 0;
                    else if (board[i][j] == 0 && count == 2) next = 0;
                    else next = 1;
                    board[i][j] |= next << 1; // put next state in the second last bit
                }
            }
            // convert temporary states to valid states
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    board[i][j] >>= 1; // replace current state bit with next state bit
                }
            }
        }

        private int isLive(int[][] board, int i, int j) {
            if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) return 0;
            return board[i][j] & 1; // check only current state bit
        }
    }
}
