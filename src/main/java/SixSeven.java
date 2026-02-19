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
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final String FILE_PATH = "data/sixseven.txt";
    private static final Storage storage = new Storage(FILE_PATH);

    public static void main(String[] args) {

        tasks.addAll(storage.load());

        System.out.println("Hello! I'm " + BOT_NAME);
        System.out.println("What can I do for you?");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String line = scanner.nextLine();

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
                    printAddDescription();
                    storage.save(tasks);
                    break;

                case "deadline":
                    int byIdx = description.indexOf(" /by ");
                    String by = description.substring(byIdx + 5).trim();
                    String deadlineDescription = description.substring(0, byIdx);

                    tasks.add(new Deadline(deadlineDescription, by));
                    printAddDescription();
                    storage.save(tasks);
                    break;

                case "event":
                    int fromIdx = description.indexOf(" /from ");
                    int toIdx = description.indexOf(" /to ");

                    String eventDescription = description.substring(0, fromIdx).trim();
                    String from = description.substring(fromIdx + 7, toIdx).trim();
                    String to = description.substring(toIdx + 5).trim();

                    tasks.add(new Event(eventDescription, from, to));
                    printAddDescription();
                    storage.save(tasks);
                    break;

                case "delete":
                    int deleteIndex = Integer.parseInt(description) - 1;
                    Task removedTask = tasks.remove(deleteIndex);

                    System.out.println("Noted. I've removed this task:");
                    System.out.println(" " + removedTask);
                    printTaskCountAfterUpdate();
                    storage.save(tasks);
                    break;

                case "list":
                    listTask();
                    break;

                case "mark":
                    markTask(description);
                    storage.save(tasks);
                    break;

                case "unmark":
                    unmarkTask(description);
                    storage.save(tasks);
                    break;

                default:
                    throw new UnknownCommandException("What do you mean!!!! >:(");
                }

            } catch (SixSevenException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void checkErrors(String command, String description)
            throws SixSevenException {

        description = description.trim().replaceAll("\\s+", " ");

        if ((command.equals("todo")
                || command.equals("deadline")
                || command.equals("event"))
                && description.isBlank()) {
            throw new EmptyException("Description cannot be blank!");
        }

        if (command.equals("deadline")) {
            int byIdx = description.indexOf(" /by ");

            if (byIdx == -1) {
                throw new InvalidFormatException("Deadline format: [description] /by [date]");
            }

            String before = description.substring(0, byIdx).trim();
            String after = description.substring(byIdx + 5).trim();

            if (before.isBlank() || after.isBlank()) {
                throw new InvalidFormatException("Deadline must contain description and date.");
            }
        }

        if (command.equals("event")) {
            int fromIdx = description.indexOf(" /from ");
            int toIdx = description.indexOf(" /to ");

            if (fromIdx == -1 || toIdx == -1 || fromIdx >= toIdx) {
                throw new InvalidFormatException(
                        "Event format: description /from START /to END");
            }

            String beforeFrom = description.substring(0, fromIdx).trim();
            String afterFrom = description.substring(fromIdx + 7, toIdx).trim();
            String afterTo = description.substring(toIdx + 5).trim();

            if (beforeFrom.isBlank()
                    || afterFrom.isBlank()
                    || afterTo.isBlank()) {
                throw new InvalidFormatException(
                        "Event must contain description, start time, and end time.");
            }
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

            int index = taskIdx - 1;

            if (command.equals("mark") && tasks.get(index).getIsDone()) {
                throw new InvalidFormatException("Task " + taskIdx + " is already done.");
            }

            if (command.equals("unmark") && !tasks.get(index).getIsDone()) {
                throw new InvalidFormatException("Task " + taskIdx + " is not done yet.");
            }
        }

        if (command.equals("delete")) {
            int taskIdx;

            try {
                taskIdx = Integer.parseInt(description);
            } catch (NumberFormatException e) {
                throw new InvalidFormatException("Delete must be followed by a number.");
            }

            if (taskIdx < 1 || taskIdx > tasks.size()) {
                throw new InvalidFormatException("No such item to delete. Check item index again.");
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
        System.out.println("[" + tasks.get(numberToMark).markString() + "] "
                + tasks.get(numberToMark).getDescription());
    }

    public static void unmarkTask(String input) {
        int numberToUnmark = Integer.parseInt(input) - 1;
        tasks.get(numberToUnmark).setIsNotDone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("[" + tasks.get(numberToUnmark).markString() + "] "
                + tasks.get(numberToUnmark).getDescription());
    }

    public static void printAddDescription() {
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + tasks.get(tasks.size() - 1));
        printTaskCountAfterUpdate();
    }

    private static void printTaskCountAfterUpdate() {
        int size = tasks.size();
        if (size == 1) {
            System.out.println("Now you have 1 task in the list.");
        } else {
            System.out.println("Now you have " + size + " tasks in the list.");
        }
    }
}
