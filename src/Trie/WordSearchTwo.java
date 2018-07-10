/**
 * LeetCode #212, hard
 *
 * Given a 2D board and a list of words from the dictionary, find all words in the board.

 Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those
 horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

 Example:

 Input:
 words = ["oath","pea","eat","rain"] and board =
 [
 ['o','a','a','n'],
 ['e','t','a','e'],
 ['i','h','k','r'],
 ['i','f','l','v']
 ]

 Output: ["eat","oath"]
 Note:
 You may assume that all inputs are consist of lowercase letters a-z.
 */

package Trie;

import java.util.*;

public class WordSearchTwo {
    /**
     * Solution 1: Trie
     *
     * Convert the words to trie for easier search. There are many "null" traps. One way to avoid some of them is to
     * initialize "next" array in TrieNode constructor. Remember to use hash set to store results to avoid duplicates.
     *
     * Time complexity: O(mn * min(k, mn)). Space complexity: O(min(k, mn)).
     */
    class Solution1 {
        class TrieNode {
            TrieNode[] next;
            String word;
            // can initialize next in constructor
        }

        private void insert(TrieNode root, String word) {
            TrieNode prev = root;
            for (char c : word.toCharArray()) {
                int i = c - 'a';
                if (prev.next == null) prev.next = new TrieNode[26];
                if (prev.next[i] == null) prev.next[i] = new TrieNode();
                prev = prev.next[i];
            }
            prev.word = word;
        }

        private void search(char[][] board, int i, int j, TrieNode root, Set<String> res) {
            if (root == null || root.next == null) return;
            if (i < 0 || i > board.length-1 || j < 0 || j > board[0].length-1) return;
            if (board[i][j] == '$') return;
            int nextIndex = board[i][j] - 'a';
            TrieNode nextRoot = root.next[nextIndex];
            if (nextRoot == null) return;
            if (nextRoot.word != null) res.add(nextRoot.word);
            char temp = board[i][j];
            board[i][j] = '$'; // mark as visited
            search(board, i+1, j, nextRoot, res);
            search(board, i-1, j, nextRoot, res);
            search(board, i, j+1, nextRoot, res);
            search(board, i, j-1, nextRoot, res);
            board[i][j] = temp;
        }

        public List<String> findWords(char[][] board, String[] words) {
            if (board == null || board.length == 0 || board[0].length == 0) return new ArrayList<>();
            TrieNode trie = new TrieNode();
            for (String word : words) insert(trie, word);
            Set<String> res = new HashSet<>();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    search(board, i, j, trie, res);
                }
            }
            return new ArrayList<>(res);
        }
    }
}
