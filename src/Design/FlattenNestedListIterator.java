/**
 * LeetCode #341, medium
 *
 * Given a nested list of integers, implement an iterator to flatten it.
 *
 * Each element is either an integer, or a list -- whose elements may also be integers or other lists.
 *
 * Example 1:
 *
 * Input: [[1,1],2,[1,1]]
 * Output: [1,1,2,1,1]
 * Explanation: By calling next repeatedly until hasNext returns false,
 *              the order of elements returned by next should be: [1,1,2,1,1].
 * Example 2:
 *
 * Input: [1,[4,[6]]]
 * Output: [1,4,6]
 * Explanation: By calling next repeatedly until hasNext returns false,
 *              the order of elements returned by next should be: [1,4,6].
 */

package Design;

import java.util.*;

public class FlattenNestedListIterator {
    /**
     * This is the interface that allows for creating nested lists.
     * You should not implement it, or speculate about its implementation
     */
    public interface NestedInteger {
        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }

    /**
     * Solution 1:
     *
     * Flatten the data structure in constructor recursively.
     *
     * Pro: Easy to write, fast next() and hasNext() calls.
     *
     * Cons: Require some pre-processing, may waste time if next() and hasNext() will only be called a limited number
     * of times.
     */
    public class NestedIterator1 implements Iterator<Integer> {
        private Iterator<Integer> iterator;

        public NestedIterator1(List<NestedInteger> nestedList) {
            List<Integer> list = new ArrayList<>();
            flatten(nestedList, list);
            iterator = list.iterator();
        }

        private void flatten(List<NestedInteger> data, List<Integer> flattened) {
            for (NestedInteger entry : data) {
                if (entry.isInteger()) flattened.add(entry.getInteger());
                else flatten(entry.getList(), flattened);
            }
        }

        @Override
        public Integer next() {
            return iterator.next();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }
    }

    /**
     * Solution 2:
     *
     * Flatten the data as we go, use a stack to store iterators. Every time hasNext() is called, peek the top of
     * stack and check if it has next element. If it has an integer, record it; If it has a list, push it to the
     * stack top; If it is empty, pop it out and continue until stack is empty.
     *
     * Tricky part is to check for nulls and nullify variable as a flag that the number has been checked out in next()
     * calls to ensure we don't checkout the same value again and again.
     *
     * Pros: Little overhead in constructor.
     *
     * Cons: More expensive next() and hasNext() calls. hasNext() might take O(n) time worst case.
     */
    public class NestedIterator2 implements Iterator<Integer> {
        Integer next;
        Stack<Iterator<NestedInteger>> stack;

        public NestedIterator2(List<NestedInteger> nestedList) {
            stack = new Stack<>();
            stack.push(nestedList.iterator());
        }

        @Override
        public Integer next() {
            if (next == null) {
                if (!hasNext()) throw new java.util.NoSuchElementException();
            }
            Integer val = next; // need to copy ref ...
            next = null; // and nullify here as a flag to show that we have called next()
            return val;
        }

        @Override
        public boolean hasNext() {
            next = null;
            while (!stack.isEmpty()) {
                Iterator<NestedInteger> curIter = stack.peek();
                if (curIter.hasNext()) {
                    NestedInteger cur = curIter.next();
                    if (cur.isInteger()) {
                        next = cur.getInteger();
                        break;
                    } else {
                        stack.push(cur.getList().iterator());
                    }
                } else {
                    stack.pop();
                }
            }
            return next != null;
        }
    }
}
