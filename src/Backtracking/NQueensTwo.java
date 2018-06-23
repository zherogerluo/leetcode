/**
 * LeetCode #52, hard
 *
 * The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.

 Given an integer n, return the number of distinct solutions to the n-queens puzzle.

 Example:

 Input: 4
 Output: 2
 Explanation: There are two distinct solutions to the 4-queens puzzle as shown below.
 [
 [".Q..",  // Solution 1
 "...Q",
 "Q...",
 "..Q."],

 ["..Q.",  // Solution 2
 "Q...",
 "...Q",
 ".Q.."]
 ]
 */

package Backtracking;

public class NQueensTwo {
    /**
     * Solution 1: Backtracking
     *
     * Same as Problem #51, except returning the number of solutions.
     */
    class Solution1 {
        char[][] board;
        boolean[] column, diag1, diag2;
        int res;

        public int totalNQueens(int n) {
            board = new char[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    board[i][j] = '.';
                }
            }
            column = new boolean[n]; diag1 = new boolean[2*n-1]; diag2 = new boolean[2*n-1];
            res = 0;
            tryPut(0, n);
            return res;
        }

        private void tryPut(int i, int n) {
            for (int j = 0; j < n; j++) {
                if (column[j] || diag1[i+j] || diag2[n-i-1+j]) continue;
                board[i][j] = 'Q';
                column[j] = true; diag1[i+j] = true; diag2[n-i-1+j] = true;
                if (i == n-1) res++;
                else tryPut(i+1, n);
                board[i][j] = '.';
                column[j] = false; diag1[i+j] = false; diag2[n-i-1+j] = false;
            }
        }
    }
}
