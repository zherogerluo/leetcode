/**
 * LeetCode #449, medium
 *
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be
 * stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in
 * the same or another computer environment.
 *
 * Design an algorithm to serialize and deserialize a binary search tree. There is no restriction on how your
 * serialization/deserialization algorithm should work. You just need to ensure that a binary search tree can be
 * serialized to a string and this string can be deserialized to the original tree structure.
 *
 * The encoded string should be as compact as possible.
 *
 * Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms
 * should be stateless.
 */

package Tree;

public class SerializeAndDeserializeBST {
    /**
     * Solution 1: Recursive pre-order
     *
     * The difference between (de-)serializing a BST and a generic binary tree is that, BST implies a known in-order
     * traversal according to node values, so we can dump null nodes when serializing BST to save space, and
     * deserializing a BST would be the same as reconstructing it with pre-order and in-order traversals.
     *
     * This solution uses recursive pre-order. Serialization is trivial. A StringBuilder is used to avoid building
     * string repeatedly. When deserializing, because it is a BST, its left branch nodes will always contain values
     * that are smaller than root value, so we search the first node that has larger value than root value, and this
     * node will be the root of right branch. Then the entire input can be split to two halves, and we recursively
     * deserialize the two parts.
     *
     * Note that the time complexity of deserialization process looks like O(n^2) average but it really is not.
     * Consider the total operations for the leaf level of a balanced BST (important, average case) for example.
     * There are n/2 leaf nodes, however for each leaf node, the deserialized data is already trimmed to be a single
     * value, so the appeared-to-be O(n) linear search is actually one operation. Likewise, it is two operations for
     * level above, etc., and n/2 operations for root level. The overall time complexity is hence n/2 * log(n) which
     * is O(n * log(n)).
     *
     * Time complexity: O(n) serialization, O(n * log(n)) deserialization.
     * Space complexity: O(log(n)) serialization, O(log(n)) deserialization, not considering serialized data.
     */
    public class Codec1 {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) return "";
            StringBuilder sb = new StringBuilder();
            serialize(root, sb);
            return sb.deleteCharAt(sb.length()-1).toString();
        }

        private void serialize(TreeNode root, StringBuilder sb) {
            if (root == null) return;
            sb.append(String.valueOf(root.val)).append(" ");
            serialize(root.left, sb);
            serialize(root.right, sb);
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if (data == null || data.equals("")) return null;
            String[] tokens = data.split(" ");
            int[] values = new int[tokens.length];
            for (int i = 0; i < tokens.length; i++) values[i] = Integer.parseInt(tokens[i]);
            return deserialize(values, 0, values.length);
        }

        private TreeNode deserialize(int[] values, int start, int end) {
            if (start >= end) return null;
            TreeNode root = new TreeNode(values[start++]);
            int i = start;
            for (; i < end; i++) {
                if (values[i] > root.val) break;
            }
            root.left = deserialize(values, start, i);
            root.right = deserialize(values, i, end);
            return root;
        }
    }

    /**
     * Solution 2: Iterative pre-order
     */
    // TODO
}
