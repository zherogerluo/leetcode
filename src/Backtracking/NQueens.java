/**
 * LeetCode #51, hard
 *
 * The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.

 Given an integer n, return all distinct solutions to the n-queens puzzle.

 Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a
 queen and an empty space respectively.

 Example:

 Input: 4
 Output: [
 [".Q..",  // Solution 1
 "...Q",
 "Q...",
 "..Q."],

 ["..Q.",  // Solution 2
 "Q...",
 "...Q",
 ".Q.."]
 ]
 Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above.
 */

package Backtracking;

import java.util.*;

public class NQueens {
    /**
     * Solution 1: Backtracking
     *
     * Put Queen LINE BY LINE, each Queen will occupy a row (already enforced), a column, and two diagonals. Put one
     * at a position only if its column and two diagonals have not been occupied. Once completing the last row, we
     * know we have a valid solution.
     *
     * The most important thing to note is, for each row there has to be only one Queen. By implementing this, we can
     * reduce time complexity from naive backtracking's O((n^2)!) to O(n!).
     *
     * Another important note is that, to verify if a position can be taken, naively we can start from that position
     * and search 8 directions to the edge of the board. However this search will take O(n) time. A better algorithm
     * would be, to keep a boolean map (array) for every column (total of n) and diagonals (total of 2 * n - 1 for +-
     * 45 degrees each). This reduces the search to O(1) time.
     *
     * Time complexity: O(n!). Space complexity: O(n).
     */
    class Solution {
        char[][] board;
        boolean[] column, diag1, diag2; // diag1 is -45 degree diagonal, diag2 is +45 degree diagonal

        public List<List<String>> solveNQueens(int n) {
            board = new char[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    board[i][j] = '.';
                }
            }
            column = new boolean[n]; diag1 = new boolean[2*n-1]; diag2 = new boolean[2*n-1];
            List<List<String>> res = new ArrayList<>();
            put(0, n, res);
            return res;
        }

        /* Put a Queen in row i */
        private void put(int i, int n, List<List<String>> res) {
            for (int j = 0; j < n; j++) {
                if (column[j] || diag1[i+j] || diag2[n-i-1+j]) continue;
                board[i][j] = 'Q';
                column[j] = true; diag1[i+j] = true; diag2[n-i-1+j] = true;
                if (i == n-1) addResult(board, res);
                else put(i+1, n, res);
                board[i][j] = '.';
                column[j] = false; diag1[i+j] = false; diag2[n-i-1+j] = false;
            }
        }

        private void addResult(char[][] board, List<List<String>> res) {
            List<String> strs = new ArrayList<>();
            for (char[] row : board) {
                strs.add(new String(row));
            }
            res.add(strs);
        }
    }
}
