package utils;

import java.util.ArrayList;
import task.Task;

public class Helpers {
    public static void printAddDescription(ArrayList<Task> tasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + tasks.get(tasks.size() - 1));
        printTaskCountAfterUpdate(tasks);
    }

    public static void printTaskCountAfterUpdate(ArrayList<Task> tasks) {
        int size = tasks.size();
        if (size == 1) {
            System.out.println("Now you have 1 task in the list.");
        } else {
            System.out.println("Now you have " + size + " tasks in the list.");
        }
    }
}