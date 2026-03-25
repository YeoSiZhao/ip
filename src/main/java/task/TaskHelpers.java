package task;

import java.util.ArrayList;

/**
 * Provides utility methods for performing operations on a list of tasks.
 * A <code>TaskHelpers</code> object contains static helper methods
 * to search, list, mark, and unmark tasks.
 */
public class TaskHelpers {

    /**
     * Finds and prints tasks that contain the specified keyword.
     * If no matching tasks are found, a message is displayed.
     *
     * @param keyword Keyword used to search task descriptions.
     * @param tasks   List of tasks to search within.
     */
    public static void findTasks(String keyword, ArrayList<Task> tasks) {
        System.out.println("Here are the matching tasks in your list:");

        boolean hasMatches = false;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getDescription().toLowerCase()
                    .contains(keyword.toLowerCase())) {
                System.out.println((i + 1) + "." + task);
                hasMatches = true;
            }
        }

        if (!hasMatches) {
            System.out.println("No matching tasks found.");
        }
    }

    /**
     * Prints all tasks in the list.
     * If the task list is empty, a message is displayed.
     *
     * @param tasks List of tasks to be displayed.
     */
    public static void listTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No task available");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + 1 + "." + tasks.get(i));
            }
        }
    }

    /**
     * Marks the specified task as done.
     *
     * @param input String representing the task index (1-based).
     * @param tasks List of tasks containing the task to be marked.
     */
    public static void markTask(String input, ArrayList<Task> tasks) {
        int numberToMark = Integer.parseInt(input) - 1;
        tasks.get(numberToMark).setDone(true);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("[" + tasks.get(numberToMark).markString() + "] "
                + tasks.get(numberToMark).getDescription());
    }

    /**
     * Marks the specified task as not done.
     *
     * @param input String representing the task index (1-based).
     * @param tasks List of tasks containing the task to be unmarked.
     */
    public static void unmarkTask(String input, ArrayList<Task> tasks) {
        int numberToUnmark = Integer.parseInt(input) - 1;
        tasks.get(numberToUnmark).setDone(false);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("[" + tasks.get(numberToUnmark).markString() + "] "
                + tasks.get(numberToUnmark).getDescription());
    }
}
