/**
 * LeetCode #745, hard
 *
 * Design a special dictionary which has some words and allows you to search the words in it by a prefix and a suffix.
 *
 * Implement the WordFilter class:
 *
 * WordFilter(string[] words) Initializes the object with the words in the dictionary.
 * f(string prefix, string suffix) Returns the index of the word in the dictionary which has the prefix prefix and the
 * suffix suffix. If there is more than one valid index, return the largest of them. If there is no such word in the
 * dictionary, return -1.
 *
 * Example 1:
 *
 * Input
 * ["WordFilter", "f"]
 * [[["apple"]], ["a", "e"]]
 * Output
 * [null, 0]
 *
 * Explanation
 * WordFilter wordFilter = new WordFilter(["apple"]);
 * wordFilter.f("a", "e"); // return 0, because the word at index 0 has prefix = "a" and suffix = 'e".
 *
 * Constraints:
 *
 * 1 <= words.length <= 15000
 * 1 <= words[i].length <= 10
 * 1 <= prefix.length, suffix.length <= 10
 * words[i], prefix and suffix consist of lower-case English letters only.
 * At most 15000 calls will be made to the function f.
 */

package Trie;

import java.util.*;

public class PrefixAndSuffixSearch {
    /**
     * Solution 1: Trie
     *
     * Trie node remembers word and index if it is the end. Prefix search is trivial, for suffix we traverse the rest
     * of trie and check if word ends with suffix and put the index into a list.
     *
     * There are a lot of other ways to approach this. For example, trie node can store every word that reaches this
     * node, or even the index only. We can also use two tries, one for prefix and the other for suffix, and find the
     * intersection.
     */
    class WordFilter {
        class Node {
            Node[] children;
            int index = -1;
            String word = null;

            Node() {
                children = new Node[26];
            }

            void addWord(String word, int index) {
                addWord(word, 0, index);
            }

            int search(String prefix, String suffix) {
                Node node = search(prefix, 0);
                if (node == null) {
                    return -1;
                }
                int res = -1;
                List<Integer> list = new ArrayList<>();
                node.traverse(suffix, list);
                for (int index : list) {
                    res = Math.max(res, index);
                }
                return res;
            }

            private void addWord(String word, int i, int index) {
                if (i == word.length()) {
                    this.index = Math.max(this.index, index);
                    this.word = word;
                    return;
                }
                int k = word.charAt(i) - 'a';
                if (children[k] == null) {
                    children[k] = new Node();
                }
                children[k].addWord(word, i+1, index);
            }

            private Node search(String prefix, int i) {
                if (i == prefix.length()) {
                    return this;
                }
                int k = prefix.charAt(i) - 'a';
                if (children[k] == null) {
                    return null;
                }
                return children[k].search(prefix, i+1);
            }

            private void traverse(String suffix, List<Integer> list) {
                if (word != null && word.endsWith(suffix)) {
                    list.add(index);
                }
                for (Node child : children) {
                    if (child != null) {
                        child.traverse(suffix, list);
                    }
                }
            }
        }

        Node root;

        public WordFilter(String[] words) {
            root = new Node();
            int index = 0;
            for (String word : words) {
                root.addWord(word, index++);
            }
        }

        public int f(String prefix, String suffix) {
            return root.search(prefix, suffix);
        }
    }
}
