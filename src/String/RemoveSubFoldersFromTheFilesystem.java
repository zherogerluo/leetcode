/**
 * LeetCode #1233, medium
 *
 * Given a list of folders, remove all sub-folders in those folders and return in any order the folders after removing.
 *
 * If a folder[i] is located within another folder[j], it is called a sub-folder of it.
 *
 * The format of a path is one or more concatenated strings of the form: / followed by one or more lowercase English
 * letters. For example, /leetcode and /leetcode/problems are valid paths while an empty string and / are not.
 *
 * Example 1:
 *
 * Input: folder = ["/a","/a/b","/c/d","/c/d/e","/c/f"]
 * Output: ["/a","/c/d","/c/f"]
 * Explanation: Folders "/a/b/" is a subfolder of "/a" and "/c/d/e" is inside of folder "/c/d" in our filesystem.
 *
 * Example 2:
 *
 * Input: folder = ["/a","/a/b/c","/a/b/d"]
 * Output: ["/a"]
 * Explanation: Folders "/a/b/c" and "/a/b/d/" will be removed because they are subfolders of "/a".
 *
 * Example 3:
 *
 * Input: folder = ["/a/b/c","/a/b/ca","/a/b/d"]
 * Output: ["/a/b/c","/a/b/ca","/a/b/d"]
 *
 * Constraints:
 *
 * 1 <= folder.length <= 4 * 10^4
 * 2 <= folder[i].length <= 100
 * folder[i] contains only lowercase letters and '/'
 * folder[i] always starts with character '/'
 * Each folder name is unique.
 */

/* Trie is probably the optimal and most natural solution */

package String;

import java.util.*;

public class RemoveSubFoldersFromTheFilesystem {
    /**
     * Solution 1:
     *
     * Use a hash set to remember all folders, take substring of each folder to see if it is already in the set. One
     * optimization used here is that, we can remember all the appeared folder lengths so we only take substring that
     * has an appeared length, without iterating through all lengths.
     */
    class Solution1 {
        public List<String> removeSubfolders(String[] folder) {
            Set<String> folders = new HashSet<>();
            Set<Integer> lengths = new HashSet<>();
            for (String f : folder) {
                folders.add(f);
                lengths.add(f.length());
            }
            List<String> res = new ArrayList<>();
            for (String f : folder) {
                boolean subfolder = false;
                for (int l : lengths) {
                    if (f.length() <= l) {
                        continue;
                    }
                    // need to make sure substring ends exclusively with '/'
                    if (folders.contains(f.substring(0, l)) && f.charAt(l) == '/') {
                        subfolder = true;
                        break;
                    }
                }
                if (subfolder) {
                    continue;
                }
                res.add(f);
            }
            return res;
        }
    }

    /**
     * Solution 2: Sorting
     *
     * If A is subfolder of B, A must be longer than B. We can sort the folders and start from the shortest, and the
     * subfolder of currently visiting folder can only be some folder we have already visited. Take substring only when
     * we see a '/', where we can also combine with the length trick used in Solution 1.
     */
    class Solution2 {
        public List<String> removeSubfolders(String[] folder) {
            Arrays.sort(folder, Comparator.comparingInt(a -> a.length()));
            Set<String> res = new HashSet<>();
            for (String f : folder) {
                boolean subfolder = false;
                for (int i = 1; i < f.length(); ++i) {
                    if (f.charAt(i) != '/') {
                        continue;
                    }
                    if (res.contains(f.substring(0, i))) {
                        subfolder = true;
                        break;
                    }
                }
                if (!subfolder) {
                    res.add(f);
                }
            }
            return new ArrayList<>(res);
        }
    }
}
