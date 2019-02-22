/**
 * LeetCode #37, hard
 *
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 *
 * A sudoku solution must satisfy all of the following rules:
 *
 * Each of the digits 1-9 must occur exactly once in each row.
 * Each of the digits 1-9 must occur exactly once in each column.
 * Each of the the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
 * Empty cells are indicated by the character '.'.
 *
 *
 * A sudoku puzzle...
 *
 *
 * ...and its solution numbers marked in red.
 *
 * Note:
 *
 * The given board contain only digits 1-9 and the character '.'.
 * You may assume that the given Sudoku puzzle will have a single unique solution.
 * The given board size is always 9x9.
 */

package HashTable;

public class SudokuSolver {
    /**
     * Solution 1: Depth-first search, backtracking
     *
     * Use DFS to fill cell with '1' to '9', and move to next empty cell, until all are filled. If any fill fails
     * (number is already taken), erase that number and go back. Classic DFS backtracking.
     *
     * Time complexity: O(1). Space complexity: O(1). Again, n is fixed to be 9.
     */
    class Solution1 {
        public void solveSudoku(char[][] board) {
            if (board == null || board.length != 9 || board[0].length != 9) {
                throw new RuntimeException("Invalid input.");
            }
            fill(board, 0);
        }

        private boolean fill(char[][] board, int index) {
            if (index >= 81) return true; // edge case
            int i = index / 9, j = index % 9;
            if (board[i][j] != '.') return fill(board, index + 1); // fast forward to next cell
            for (char c = '1'; c <= '9'; c++) {
                if (!isValid(board, i, j, c)) continue;
                board[i][j] = c;
                if (fill(board, index + 1)) return true;
                board[i][j] = '.'; // erase the cell before going back
            }
            return false;
        }

        private boolean isValid(char[][] board, int i, int j, char target) {
            for (char c : board[i]) {
                if (c == target) return false;
            }
            for (int r = 0; r < 9; r++) {
                if (board[r][j] == target) return false;
            }
            int iOffset = i / 3 * 3, jOffset = j / 3 * 3;
            for (int k = 0; k < 9; k++) {
                if (board[iOffset+k/3][jOffset+k%3] == target) return false;
            }
            return true;
        }
    }

    /**
     * Solution 2:
     *
     * Same as Solution 1, except that it calculates available numbers before go into DFS loop, which saves some time
     * comparing with calling isValid() multiple times.
     */
    class Solution2 {
        public void solveSudoku(char[][] board) {
            if (board == null || board.length != 9 || board[0].length != 9) {
                throw new RuntimeException("Invalid input.");
            }
            fill(board, 0);
        }

        private boolean fill(char[][] board, int index) {
            if (index >= 81) return true;
            int i = index / 9, j = index % 9;
            if (board[i][j] != '.') return fill(board, index + 1);
            int[] used = findUsed(board, i, j);
            for (int k = 0; k < 9; k++) {
                if (used[k] == 1) continue;
                board[i][j] = (char)('1' + k);
                if (fill(board, index + 1)) return true;
                board[i][j] = '.';
            }
            return false;
        }

        private int[] findUsed(char[][] board, int i, int j) {
            int[] used = new int[9];
            for (char c : board[i]) {
                if (c != '.') used[c-'1'] = 1;
            }
            for (int r = 0; r < 9; r++) {
                if (board[r][j] != '.') used[board[r][j]-'1'] = 1;
            }
            int iOffset = i / 3 * 3, jOffset = j / 3 * 3;
            for (int k = 0; k < 9; k++) {
                if (board[iOffset+k/3][jOffset+k%3] != '.') {
                    used[board[iOffset+k/3][jOffset+k%3]-'1'] = 1;
                }
            }
            return used;
        }
    }
}
