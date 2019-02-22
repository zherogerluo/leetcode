/**
 * LeetCode #36, medium
 *
 * Determine if a 9x9 Sudoku board is valid. Only the filled cells need to be validated according to the following rules:
 *
 * Each row must contain the digits 1-9 without repetition.
 * Each column must contain the digits 1-9 without repetition.
 * Each of the 9 3x3 sub-boxes of the grid must contain the digits 1-9 without repetition.
 *
 * A partially filled sudoku which is valid.
 *
 * The Sudoku board could be partially filled, where empty cells are filled with the character '.'.
 *
 * Example 1:
 *
 * Input:
 * [
 *   ["5","3",".",".","7",".",".",".","."],
 *   ["6",".",".","1","9","5",".",".","."],
 *   [".","9","8",".",".",".",".","6","."],
 *   ["8",".",".",".","6",".",".",".","3"],
 *   ["4",".",".","8",".","3",".",".","1"],
 *   ["7",".",".",".","2",".",".",".","6"],
 *   [".","6",".",".",".",".","2","8","."],
 *   [".",".",".","4","1","9",".",".","5"],
 *   [".",".",".",".","8",".",".","7","9"]
 * ]
 * Output: true
 *
 * Example 2:
 *
 * Input:
 * [
 *   ["8","3",".",".","7",".",".",".","."],
 *   ["6",".",".","1","9","5",".",".","."],
 *   [".","9","8",".",".",".",".","6","."],
 *   ["8",".",".",".","6",".",".",".","3"],
 *   ["4",".",".","8",".","3",".",".","1"],
 *   ["7",".",".",".","2",".",".",".","6"],
 *   [".","6",".",".",".",".","2","8","."],
 *   [".",".",".","4","1","9",".",".","5"],
 *   [".",".",".",".","8",".",".","7","9"]
 * ]
 * Output: false
 *
 * Explanation: Same as Example 1, except with the 5 in the top left corner being
 *     modified to 8. Since there are two 8's in the top left 3x3 sub-box, it is invalid.
 *
 * Note:
 *
 * A Sudoku board (partially filled) could be valid but is not necessarily solvable.
 * Only the filled cells need to be validated according to the mentioned rules.
 * The given board contain only digits 1-9 and the character '.'.
 * The given board size is always 9x9.
 */

package HashTable;

public class ValidSudoku {
    /**
     * Solution 1: Integer bits as hash table
     *
     * For the i-th row/column/box, use the last 10 bit of integer to store whether that number has been taken. To do
     * all three loops in one pass, the index of box tile needs careful thought. Basically, figure out the row and
     * column offsets (i/3*3 and 1%3*3), then add them to ordinary matrix indexing with j.
     *
     * Time complexity: O(1). Space complexity: O(1). n is always 9.
     */
    class Solution1 {
        public boolean isValidSudoku(char[][] board) {
            for (int i = 0; i < 9; i++) {
                int rowStatus = 0, colStatus = 0, boxStatus = 0;
                for (int j = 0; j < 9; j++) {
                    int rowDigit = board[i][j] - '0',
                        colDigit = board[j][i] - '0',
                        boxDigit = board[i/3*3+j/3][i%3*3+j%3] - '0';
                    if (rowDigit >= 1 && rowDigit <= 9) {
                        if ((rowStatus & 1 << rowDigit) != 0) return false;
                        rowStatus = rowStatus | 1 << rowDigit;
                    }
                    if (colDigit >= 1 && colDigit <= 9) {
                        if ((colStatus & 1 << colDigit) != 0) return false;
                        colStatus = colStatus | 1 << colDigit;
                    }
                    if (boxDigit >= 1 && boxDigit <= 9) {
                        if ((boxStatus & 1 << boxDigit) != 0) return false;
                        boxStatus = boxStatus | 1 << boxDigit;
                    }
                }
            }
            return true;
        }
    }

    /**
     * Solution 2:
     *
     * Instead of a little complicated indexing, this solution iterates through the matrix in ordinary way, and
     * record its presence for corresponding row/column/box.
     */
    class Solution2 {
        public boolean isValidSudoku(char[][] board) {
            int[] rowStatus = new int[9], colStatus = new int[9], boxStatus = new int[9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (board[i][j] == '.') continue;
                    int digit = board[i][j] - '0';
                    int iRow = i, iCol = j, iBox = i/3*3+j/3;
                    if ((rowStatus[iRow] & 1 << digit) != 0) return false;
                    rowStatus[iRow] = rowStatus[iRow] | 1 << digit;
                    if ((colStatus[iCol] & 1 << digit) != 0) return false;
                    colStatus[iCol] = colStatus[iCol] | 1 << digit;
                    if ((boxStatus[iBox] & 1 << digit) != 0) return false;
                    boxStatus[iBox] = boxStatus[iBox] | 1 << digit;
                }
            }
            return true;
        }
    }
}
