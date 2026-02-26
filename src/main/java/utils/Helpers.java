package utils;

import java.util.ArrayList;
import task.Task;

/**
 * Provides utility methods for printing task-related messages.
 * A <code>Helpers</code> object contains static helper methods
 * to display updates after task list modifications.
 */
public class Helpers {

    /**
     * Prints confirmation after a task has been added.
     * Also displays the updated task count.
     *
     * @param tasks List of tasks after the new task has been added.
     */
    public static void printAddDescription(ArrayList<Task> tasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + tasks.get(tasks.size() - 1));
        printTaskCountAfterUpdate(tasks);
    }

    /**
     * Prints the current number of tasks in the list.
     *
     * @param tasks List of tasks to count.
     */
    public static void printTaskCountAfterUpdate(ArrayList<Task> tasks) {
        int size = tasks.size();
        if (size == 1) {
            System.out.println("Now you have 1 task in the list.");
        } else {
            System.out.println("Now you have " + size + " tasks in the list.");
        }
    }
}