/**
 * LeetCode #1700, easy
 *
 * The school cafeteria offers circular and square sandwiches at lunch break, referred to by numbers 0 and 1
 * respectively. All students stand in a queue. Each student either prefers square or circular sandwiches.
 *
 * The number of sandwiches in the cafeteria is equal to the number of students. The sandwiches are placed in a stack.
 * At each step:
 *
 * If the student at the front of the queue prefers the sandwich on the top of the stack, they will take it and leave
 * the queue. Otherwise, they will leave it and go to the queue's end. This continues until none of the queue students
 * want to take the top sandwich and are thus unable to eat.
 *
 * You are given two integer arrays students and sandwiches where sandwiches[i] is the type of the ith sandwich in the
 * stack (i = 0 is the top of the stack) and students[j] is the preference of the jth student in the initial queue
 * (j = 0 is the front of the queue). Return the number of students that are unable to eat.
 *
 * Example 1:
 *
 * Input: students = [1,1,0,0], sandwiches = [0,1,0,1]
 * Output: 0
 * Explanation:
 * - Front student leaves the top sandwich and returns to the end of the line making students = [1,0,0,1].
 * - Front student leaves the top sandwich and returns to the end of the line making students = [0,0,1,1].
 * - Front student takes the top sandwich and leaves the line making students = [0,1,1] and sandwiches = [1,0,1].
 * - Front student leaves the top sandwich and returns to the end of the line making students = [1,1,0].
 * - Front student takes the top sandwich and leaves the line making students = [1,0] and sandwiches = [0,1].
 * - Front student leaves the top sandwich and returns to the end of the line making students = [0,1].
 * - Front student takes the top sandwich and leaves the line making students = [1] and sandwiches = [1].
 * - Front student takes the top sandwich and leaves the line making students = [] and sandwiches = [].
 * Hence all students are able to eat.
 *
 * Example 2:
 *
 * Input: students = [1,1,1,0,0,1], sandwiches = [1,0,0,0,1,1]
 * Output: 3
 *
 * Constraints:
 *
 * 1 <= students.length, sandwiches.length <= 100
 * students.length == sandwiches.length
 * sandwiches[i] is 0 or 1.
 * students[i] is 0 or 1.
 */

package Array;

import java.util.*;

public class NumberOfStudentsUnableToEatLunch {
    /**
     * Solution 1: Queue and stack
     *
     * Solve the problem by definition, use a queue for students and a stack for sandwiches. To terminate the loop, we
     * need to remember queue size at beginning and see if it remains the same at end of loop.
     */
    class Solution1 {
        public int countStudents(int[] students, int[] sandwiches) {
            Deque<Integer> queue = new ArrayDeque<>();
            Deque<Integer> stack = new ArrayDeque<>();
            for (int student : students) {
                queue.offer(student);
            }
            for (int sandwich : sandwiches) {
                stack.offerLast(sandwich);
            }
            while (!queue.isEmpty() && !stack.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; ++i) {
                    int student = queue.poll();
                    if (stack.peek() == student) {
                        stack.pop();
                    } else {
                        queue.offer(student);
                    }
                }
                if (queue.size() == size) {
                    return size;
                }
            }
            return queue.size();
        }
    }

    /**
     * Solution 2:
     *
     * Same idea but we lump together each round: Count total zeros and ones, decrement them "as if" we went through
     * one round of polling and queueing students to consume the sandwiches, return if we need zero/one but don't have
     * availability.
     */
    class Solution2 {
        public int countStudents(int[] students, int[] sandwiches) {
            int ones = 0, zeros = students.length;
            for (int student : students) {
                ones += student;
                zeros -= student;
            }
            for (int sandwich : sandwiches) {
                if (sandwich == 0) {
                    if (zeros == 0) {
                        // need zero to consume this sandwich but don't have any
                        return ones;
                    }
                    // consume
                    --zeros;
                } else {
                    if (ones == 0) {
                        // need one to consume this sandwich but don't have any
                        return zeros;
                    }
                    // consume
                    --ones;
                }
            }
            return zeros + ones;
        }
    }
}
