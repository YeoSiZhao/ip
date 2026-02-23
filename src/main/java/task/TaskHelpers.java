package task;

import java.util.ArrayList;

public class TaskHelpers {
    public static void findTasks(String keyword, ArrayList<Task> tasks) {
        System.out.println("Here are the matching tasks in your list:");

        int counter = 1;
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase()
                    .contains(keyword.toLowerCase())) {

                System.out.println(" " + counter + "." + task);
                counter++;
            }
        }

        if (counter == 1) {
            System.out.println("No matching tasks found.");
        }
    }

    public static void listTask(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No task available");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + 1 + "." + tasks.get(i));
            }
        }
    }

    public static void markTask(String input, ArrayList<Task> tasks) {
        int numberToMark = Integer.parseInt(input) - 1;
        tasks.get(numberToMark).setIsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("[" + tasks.get(numberToMark).markString() + "] "
                + tasks.get(numberToMark).getDescription());
    }

    public static void unmarkTask(String input,ArrayList<Task> tasks) {
        int numberToUnmark = Integer.parseInt(input) - 1;
        tasks.get(numberToUnmark).setIsNotDone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("[" + tasks.get(numberToUnmark).markString() + "] "
                + tasks.get(numberToUnmark).getDescription());
    }
}
