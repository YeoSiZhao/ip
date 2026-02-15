import java.util.Scanner;
import java.util.ArrayList;

import errors.EmptyException;
import errors.InvalidFormatException;
import errors.SixSevenException;
import errors.UnknownCommandException;
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;

public class SixSeven {

    public static final String BOT_NAME = "SixSeven";
    private static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Hello! I'm " + BOT_NAME);
        System.out.println("What can I do for you?");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String line;
                line = scanner.nextLine();

                String[] input = line.split(" ", 2);
                String command = input[0];
                String description = input.length > 1 ? input[1] : "";
                checkErrors(command, description);

                switch (command) {
                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");
                    scanner.close();
                    return;
                case "todo":
                    tasks.add(new Todo(description));
                    printDescription();
                    break;
                case "deadline":
                    int byIdx = description.indexOf("/by");

                    String by = description.substring(byIdx + 4).trim();
                    String deadlineDescription = description.substring(0, byIdx);

                    tasks.add(new Deadline(deadlineDescription, by));
                    printDescription();
                    break;
                case "event":
                    int fromIdx = description.indexOf("/from");
                    int toIdx = description.indexOf("/to");

                    String eventDescription = description.substring(0, fromIdx).trim();
                    String from = description.substring(fromIdx + 6, toIdx).trim();
                    String to = description.substring(toIdx + 4).trim();

                    tasks.add(new Event(eventDescription, from, to));
                    printDescription();
                    break;
                case "list":
                    listTask();
                    break;
                case "mark":
                    markTask(input[1]);
                    break;
                case "unmark":
                    unmarkTask(input[1]);
                    break;
                default:
                    throw new UnknownCommandException("What do you mean!!!! >:(");
                }
            } catch (SixSevenException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void checkErrors(String command, String description) throws SixSevenException {
        // Commands that require a description
        if ((command.equals("todo") || command.equals("deadline") || command.equals("event")) && description.isBlank()) {
            throw new EmptyException("Description cannot be blank!");
        }

        // deadline must contain /by
        if (command.equals("deadline") && !description.contains("/by")) {
            throw new InvalidFormatException("Deadline must consist of /by");
        }

        // event must contain both /from and /to
        if (command.equals("event") && (!description.contains("/from") || !description.contains("/to"))) {
            throw new InvalidFormatException("Event must consist of /from and /to");
        }

        if (command.equals("mark") || command.equals("unmark")) {
            int taskIdx;

            try {
                taskIdx = Integer.parseInt(description);
            } catch (NumberFormatException e) {
                throw new InvalidFormatException("Mark and unmark must be followed by a number.");
            }

            if (taskIdx < 1 || taskIdx > tasks.size()) {
                throw new InvalidFormatException("Task number is out of range.");
            }

            int internalIdx = taskIdx - 1;

            if (command.equals("unmark") && !tasks.get(internalIdx).getIsDone()) {
                throw new InvalidFormatException("Task " + description + " is not done yet.");
            }
            if (command.equals("mark") && tasks.get(internalIdx).getIsDone()) {
                throw new InvalidFormatException("Task " + description + " is already done.");
            }
        }
    }


    public static void listTask() {
        if (tasks.isEmpty()) {
            System.out.println("No task available");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + 1 + "." + tasks.get(i));
            }
        }
    }

    public static void markTask(String input) {
        int numberToMark = Integer.parseInt(input) - 1;
        tasks.get(numberToMark).setIsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("[" + tasks.get(numberToMark).markString() + "] " + tasks.get(numberToMark).getDescription());
    }

    public static void unmarkTask(String input) {
        int numberToUnmark = Integer.parseInt(input) - 1;
        tasks.get(numberToUnmark).setIsNotDone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("[" + tasks.get(numberToUnmark).markString() + "] " + tasks.get(numberToUnmark).getDescription());
    }

    public static void printDescription() {
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + tasks.get(tasks.size() - 1));
        String strTask;
        if (tasks.size() == 1) {
            strTask = "task";
        } else {
            strTask = "tasks";
        }
        System.out.println("Now you have " + tasks.size() + " " + strTask + " in the list.");
    }
}