/**
 * LeetCode #208, medium
 *
 * Implement a trie with insert, search, and startsWith methods.

 Example:

 Trie trie = new Trie();

 trie.insert("apple");
 trie.search("apple");   // returns true
 trie.search("app");     // returns false
 trie.startsWith("app"); // returns true
 trie.insert("app");
 trie.search("app");     // returns true
 Note:

 You may assume that all inputs are consist of lowercase letters a-z.
 All inputs are guaranteed to be non-empty strings.

 */

package Design;

public class ImplementTrie {
    /**
     * Solution 1:
     *
     * Use plain array to store children in each node. No need to store char or string in the node for this problem.
     * However if we need to traverse trie and return all stored words, we can store the word in the corresponding
     * leaf node.
     */
    class Trie1 {
        class TrieNode {
            TrieNode[] next;
            boolean word;
        }

        TrieNode root;

        /** Initialize your data structure here. */
        public Trie1() {
            root = new TrieNode();
        }

        /** Inserts a word into the trie. */
        public void insert(String word) {
            TrieNode prev = root;
            for (char c : word.toCharArray()) {
                if (prev.next == null) prev.next = new TrieNode[26];
                int i = c - 'a';
                if (prev.next[i] == null) prev.next[i] = new TrieNode();
                prev = prev.next[i];
            }
            prev.word = true;
        }

        /** Returns if the word is in the trie. */
        public boolean search(String word) {
            TrieNode endNode = searchPrefix(word);
            if (endNode == null) return false;
            return endNode.word;
        }

        /** Returns if there is any word in the trie that starts with the given prefix. */
        public boolean startsWith(String prefix) {
            return searchPrefix(prefix) != null;
        }

        /** Returns the node corresponding to the last char of prefix, or null if such prefix is not found. */
        private TrieNode searchPrefix(String prefix) {
            // DRY practice
            TrieNode prev = root;
            for (char c : prefix.toCharArray()) {
                int i = c - 'a';
                if (prev.next == null || prev.next[i] == null) return null;
                prev = prev.next[i];
            }
            return prev;
        }
    }
}
