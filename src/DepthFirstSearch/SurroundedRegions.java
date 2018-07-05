/**
 * LeetCode #130, medium
 *
 * Given a 2D board containing 'X' and 'O' (the letter O), capture all regions surrounded by 'X'.

 A region is captured by flipping all 'O's into 'X's in that surrounded region.

 Example:

 X X X X
 X O O X
 X X O X
 X O X X
 After running your function, the board should be:

 X X X X
 X X X X
 X X X X
 X O X X
 Explanation:

 Surrounded regions shouldn’t be on the border, which means that any 'O' on the border of the board are not flipped
 to 'X'. Any 'O' that is not on the border and it is not connected to an 'O' on the border will be flipped to 'X'.
 Two cells are connected if they are adjacent cells connected horizontally or vertically.
 */

package DepthFirstSearch;

public class SurroundedRegions {
    /**
     * Solution 1: DFS
     *
     * A clear observation is that all 'O's connected to the edge should be kept intact, while others should be
     * turned to 'X'. So we examine those 'O's on the edges, and do a DFS to mark all 'O's connected to it. In the
     * end, do some post-processing to mark them to the right character.
     *
     * Time complexity: O(m * n) since we will at most mark this many elements. Space complexity: O(1).
     */
    class Solution1 {
        public void solve(char[][] board) {
            if (board == null || board.length == 0) return;
            final int m = board.length, n = board[0].length;
            for (int j = 0; j < n; j++) {
                if (board[0][j] == 'O') connect(board, 0, j);
                if (board[m-1][j] == 'O') connect(board, m-1, j);
            }
            for (int i = 1; i < m-1; i++) {
                if (board[i][0] == 'O') connect(board, i, 0);
                if (board[i][n-1] == 'O') connect(board, i, n-1);
            }
            // post-processing
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] == 'C') board[i][j] = 'O';
                    else if (board[i][j] == 'O') board[i][j] = 'X';
                }
            }
        }

        private void connect(char[][] board, int i, int j) {
            final int m = board.length, n = board[0].length;
            if (i < 0 || i >= m || j < 0 || j >= n) return;
            if (board[i][j] == 'O') {
                board[i][j] = 'C'; // mark the connected nodes as 'C'
                connect(board, i-1, j);
                connect(board, i+1, j);
                connect(board, i, j-1);
                connect(board, i, j+1);
            }
        }
    }
}
