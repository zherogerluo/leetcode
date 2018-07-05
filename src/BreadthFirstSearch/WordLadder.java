/**
 * LeetCode #127, medium
 *
 * Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation
 * sequence from beginWord to endWord, such that:

 Only one letter can be changed at a time.
 Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
 Note:

 Return 0 if there is no such transformation sequence.
 All words have the same length.
 All words contain only lowercase alphabetic characters.
 You may assume no duplicates in the word list.
 You may assume beginWord and endWord are non-empty and are not the same.
 Example 1:

 Input:
 beginWord = "hit",
 endWord = "cog",
 wordList = ["hot","dot","dog","lot","log","cog"]

 Output: 5

 Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 return its length 5.
 Example 2:

 Input:
 beginWord = "hit"
 endWord = "cog"
 wordList = ["hot","dot","dog","lot","log"]

 Output: 0

 Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
 */

package BreadthFirstSearch;

import java.util.*;

public class WordLadder {
    /**
     * Solution 1: BFS using queue
     *
     * For this shortest path problem, DFS is clearly a bad choice, don't even attempt it because it would be
     * extremely inefficient. Use BFS with queue, for each level we visit, increment the length by 1, and add all
     * potential new words into the queue.
     *
     * Important Note #1: Use a hash set to store all the words unvisited. We don't need to readd words to it like we
     * do for most backtracking problems because the length would only be larger (worse) for those words to reach end.
     *
     * Important Note #2: To find the new word, naive way is to search all remaining words that are one edit distance
     * away, but that will take O(k * n) time, very inefficient. Instead, we know the alphabet size, so just
     * enumerate all possible words and check if it is in the remaining words. This will take O(26 * k) = O(k) time,
     * much faster.
     *
     * Important Note #3: We need to remove the new word from unvisited as soon as adding it to the queue. Otherwise,
     * there will be duplicates in the queue and thus huge performance degradation.
     *
     * Time complexity: O(k * n). Space complexity: O(k * n). k is the length of word.
     */
    class Solution1 {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            Set<String> unvisited = new HashSet<>(wordList);
            if (!unvisited.contains(endWord)) return 0;
            Queue<String> queue = new LinkedList<>();
            unvisited.remove(beginWord);
            queue.offer(beginWord);
            int len = 1;
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    char[] cs = queue.poll().toCharArray();
                    for (int k = 0; k < cs.length; k++) {
                        char temp = cs[k];
                        for (char c = 'a'; c <= 'z'; c++) {
                            cs[k] = c;
                            String newWord = new String(cs);
                            if (newWord.equals(endWord)) return len + 1;
                            if (unvisited.contains(newWord)) {
                                queue.offer(newWord);
                                // need to remove new word immediately to prevent duplicates in queue
                                unvisited.remove(newWord);
                            }
                        }
                        cs[k] = temp; // don't forget to restore the char to its original state
                    }
                }
                len++;
            }
            return 0;
        }
    }

    /**
     * Solution 2: Bidirectional BFS using hash sets
     *
     * In Solution 1, the search space expands like a tree as the path length increments. We can do a bidirectional
     * BFS to optimize it. We keep to hash sets, one stores last visited words from left (beginWord) and the other
     * stores those from right (endWord). In the while loop, we always pick the smaller set as a starting point to
     * collect new words. Once a new word appears in the other set, the search is complete.
     *
     * The bidirectional optimization is expected to reduce the run time by a factor of 4.
     *
     * Tricky part #1: We need to use a temporary hash set (cur) to store all the new words found in current
     * iteration. We cannot concurrently remove and add words to start set while we iterate through it (otherwise
     * concurrent modification exception will arise). After the iteration, start will be replace by this cur set.
     *
     * Tricky part #2: We need to swap start and end sets depending on their sizes: Smaller one becomes start.
     *
     * Tricky part #3: The loop termination criteria is not when unvisited becomes empty. If last new word set is not
     * empty, we still has a chance to reach the end via one edit, so the right termination clause should be when
     * start becomes empty.
     */
    class Solution2 {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            if (beginWord.equals(endWord)) return 1;
            Set<String> unvisited = new HashSet<>(wordList);
            if (!unvisited.contains(endWord)) return 0;
            Set<String> start = new HashSet<>();
            Set<String> end = new HashSet<>();
            start.add(beginWord);
            end.add(endWord);
            int len = 1;
            // stopping criteria is not unvisited.isEmpty()
            while (!start.isEmpty()) { // no need to check for end, because swapping already occurred in last loop
                Set<String> cur = new HashSet<>(); // need to use a temporary set here
                for (String word : start) {
                    char[] chars = word.toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        char temp = chars[i];
                        for (char c = 'a'; c <= 'z'; c++) {
                            chars[i] = c;
                            String newWord = new String(chars);
                            if (end.contains(newWord)) return len + 1;
                            if (unvisited.contains(newWord)) {
                                unvisited.remove(newWord);
                                cur.add(newWord);
                            }
                        }
                        chars[i] = temp; // don't forget to restore the char to its original state
                    }
                }
                start = cur;
                // key to bidirectional BFS: Make sure start is the smaller set of the two
                if (start.size() > end.size()) {
                    Set<String> temp = start;
                    start = end;
                    end = temp;
                }
                len++;
            }
            return 0;
        }
    }
}
