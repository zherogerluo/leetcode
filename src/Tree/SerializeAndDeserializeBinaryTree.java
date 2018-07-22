/**
 * LeetCode #297, hard
 *
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be
 * stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in
 * the same or another computer environment.

 Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your
 serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to
 a string and this string can be deserialized to the original tree structure.

 Example:

 You may serialize the following tree:

 1
 / \
 2   3
 / \
 4   5

 as "[1,2,3,null,null,4,5]"
 Clarification: The above format is the same as how LeetCode serializes a binary tree. You do not necessarily need to
 follow this format, so please be creative and come up with different approaches yourself.

 Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms
 should be stateless.
 */

package Tree;

import java.util.*;

public class SerializeAndDeserializeBinaryTree {
    /**
     * Solution 1: Preorder, recursive
     *
     * Typical preorder serialization. To deserialize, need to keep an index reference to track progress. Terminates
     * at "" or array bound.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    public class Codec1 {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) return "";
            return root.val + "," + serialize(root.left) + "," + serialize(root.right);
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            return deserialize(data.split(","), new int[]{0});
        }

        private TreeNode deserialize(String[] data, int[] index) {
            if (index[0] >= data.length) return null; // check for index out of bound
            if (data[index[0]].equals("")) {
                index[0]++; // increment the index
                return null;
            }
            TreeNode root = new TreeNode(Integer.parseInt(data[index[0]++])); // increment the index
            root.left = deserialize(data, index);
            root.right = deserialize(data, index);
            return root;
        }
    }

    /**
     * Solution 2: Preorder, iterative
     *
     * The iterative serialization method is like the iterative preorder traversal, but note that we need to
     * explicitly deal with null nodes, so they must go into stack as well.
     *
     * The iterative deserialization method is like the iterative inorder traversal. We go through the data and push
     * nodes to stack if not seen empty data. Once we do, we need to check the next data, if empty, then pop nodes out
     * of stack which is equivalent to appending null node to the right, if not then attach right node to current
     * node and proceed to this new node.
     */
    public class Codec2 {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) return "";
            StringBuilder sb = new StringBuilder();
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                root = stack.pop();
                if (root != null) sb.append(root.val); // deal with null node
                sb.append(",");
                if (root != null) {
                    // need to push null nodes into stack as well
                    stack.push(root.right);
                    stack.push(root.left);
                }
            }
            return sb.deleteCharAt(sb.length()-1).toString(); // trim last comma
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            String[] tokens = data.split(",");
            if (tokens[0].equals("")) return null;
            Stack<TreeNode> stack = new Stack<>();
            TreeNode root = new TreeNode(Integer.parseInt(tokens[0])), cur = root;
            stack.push(root);
            int i = 1;
            while (i < tokens.length) {
                // push new nodes in stack until we see an empty data entry
                while (i < tokens.length && !tokens[i].equals("")) {
                    cur.left = new TreeNode(Integer.parseInt(tokens[i++]));
                    cur = cur.left;
                    stack.push(cur);
                }
                i++; // next data entry
                // for empty data entry, just append to current node (do nothing) and pop new node as current node
                while (i < tokens.length && tokens[i].equals("")) {
                    cur = stack.pop();
                    i++;
                }
                cur = stack.pop(); // additional pop gives parent of last node with null right child
                // now the data entry is not null, then we append new node to right and go to this new node
                if (i < tokens.length) {
                    cur.right = new TreeNode(Integer.parseInt(tokens[i++]));
                    cur = cur.right;
                    stack.push(cur);
                }
            }
            return root;
        }
    }

    /**
     * Solution 3: Inorder, iterative
     *
     * Typical inorder traversal, but like Solution 2, we need to explicitly take care of null nodes. However for
     * deserialization we don't have to do this because the null nodes won't have any children shown up in the
     * serialized results, so we don't have to remember them in the queue.
     */
    public class Codec3 {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) return "";
            Queue<TreeNode> queue = new LinkedList<>();
            StringBuilder sb = new StringBuilder();
            queue.offer(root);
            while (!queue.isEmpty()) {
                root = queue.poll();
                if (root != null) sb.append(root.val);
                sb.append(",");
                if (root != null) {
                    // need to offer null as well
                    queue.offer(root.left);
                    queue.offer(root.right);
                }
            }
            return sb.deleteCharAt(sb.length()-1).toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            String[] tokens = data.split(",");
            if (tokens[0].equals("")) return null;
            TreeNode root = new TreeNode(Integer.parseInt(tokens[0])), cur = root;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            int i = 1;
            while (i < tokens.length) {
                cur = queue.poll();
                // don't forget to check for bounds!
                TreeNode left = (i >= tokens.length || tokens[i].equals("")) ? null : new TreeNode(Integer.parseInt(tokens[i]));
                i++; // don't forget to increment
                TreeNode right = (i >= tokens.length || tokens[i].equals("")) ? null : new TreeNode(Integer.parseInt(tokens[i]));
                i++; // don't forget to increment here!
                cur.left = left;
                cur.right = right;
                // this time we don't have to offer null nodes
                if (left != null) queue.offer(left);
                if (right != null) queue.offer(right);
            }
            return root;
        }
    }
}
