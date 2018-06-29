/**
 * LeetCode #79, medium
 *
 * Given a 2D board and a word, find if the word exists in the grid.

 The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those
 horizontally or vertically neighboring. The same letter cell may not be used more than once.

 Example:

 board =
 [
 ['A','B','C','E'],
 ['S','F','C','S'],
 ['A','D','E','E']
 ]

 Given word = "ABCCED", return true.
 Given word = "SEE", return true.
 Given word = "ABCB", return false.
 */

package Array;

public class WordSearch {
    /**
     * Solution 1: Backtracking
     *
     * Typical backtracking again. Things that need attention: Index boundary check, four directions (don't copy and
     * forgot to change index), add char check in inner loop in main function to boost performance.
     *
     * Time complexity: O((m * n)^2) or O(m * n * 4^k)? Space complexity: O(k) if not using extra boolean array.
     */
    class Solution1 {
        public boolean exist(char[][] board, String word) {
            if (board == null || board.length == 0 || board[0].length == 0) return false;
            final int m = board.length, n = board[0].length;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] == word.charAt(0) && exist(board, i, j, word, 0)) return true;
                }
            }
            return false;
        }

        private boolean exist(char[][] board, int i, int j, String word, int k) {
            if (k == word.length()) return true;
            if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return false; // validate indexes
            if (board[i][j] == '$' || word.charAt(k) != board[i][j]) return false;
            char temp = board[i][j];
            board[i][j] = '$'; // record visited char in-place, if not allowed, use a boolean[m][n]
            boolean res = exist(board, i+1, j, word, k+1) ||
                    exist(board, i-1, j, word, k+1) ||
                    exist(board, i, j+1, word, k+1) ||
                    exist(board, i, j-1, word, k+1);
            board[i][j] = temp;
            return res;
        }
    }
}
