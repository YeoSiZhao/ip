import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;

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
    private static final String FILE_PATH = "data" + File.separator + "sixseven.txt";

    public static void main(String[] args) {

        loadFromFile();

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
                    System.out.println("Got it. I've added this task:");
                    printDescription();
                    saveToFile();
                    break;
                case "deadline":
                    int byIdx = description.indexOf(" /by ");

                    String by = description.substring(byIdx + 5).trim();
                    String deadlineDescription = description.substring(0, byIdx);

                    tasks.add(new Deadline(deadlineDescription, by));
                    System.out.println("Got it. I've added this task:");
                    printDescription();
                    saveToFile();
                    break;
                case "event":
                    int fromIdx = description.indexOf(" /from ");
                    int toIdx = description.indexOf(" /to ");

                    String eventDescription = description.substring(0, fromIdx).trim();
                    String from = description.substring(fromIdx + 7, toIdx).trim();
                    String to = description.substring(toIdx + 5).trim();

                    tasks.add(new Event(eventDescription, from, to));
                    System.out.println("Got it. I've added this task:");
                    printDescription();
                    saveToFile();
                    break;
                case "delete":
                    int deleteIndex = Integer.parseInt(description) - 1;
                    Task removedTask = tasks.remove(deleteIndex);

                    System.out.println("Noted. I've removed this task:");
                    System.out.println(" " + removedTask);

                    if (tasks.size() == 1) {
                        System.out.println("Now you have 1 task in the list.");
                    } else {
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    }
                    break;
                case "list":
                    listTask();
                    break;
                case "mark":
                    markTask(description);
                    saveToFile();
                    break;
                case "unmark":
                    unmarkTask(description);
                    saveToFile();
                    break;
                default:
                    throw new UnknownCommandException("What do you mean!!!! >:(");
                }
            } catch (SixSevenException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void loadFromFile() {
        try {
            Path path = Paths.get(FILE_PATH);

            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(" \\| ");

                    if (parts.length < 3) {
                        continue;
                    }

                    String type = parts[0];
                    boolean isDone = parts[1].equals("1");

                    switch (type) {
                    case "T":
                        Task todo = new Todo(parts[2]);
                        if (isDone) todo.setIsDone();
                        tasks.add(todo);
                        break;
                    case "D":
                        if (parts.length < 4) continue;
                        Task deadline = new Deadline(parts[2], parts[3]);
                        if (isDone) deadline.setIsDone();
                        tasks.add(deadline);
                        break;
                    case "E":
                        if (parts.length < 5) continue;
                        Task event = new Event(parts[2], parts[3], parts[4]);
                        if (isDone) event.setIsDone();
                        tasks.add(event);
                        break;
                    default:
                        break;
                    }

                } catch (Exception ignored) {
                }
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error loading file.");
        }
    }

    private static void saveToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));

            for (Task task : tasks) {

                String status = task.getIsDone() ? "1" : "0";

                if (task instanceof Todo) {
                    writer.write("T | " + status + " | " + task.getDescription());
                } else if (task instanceof Deadline d) {
                    writer.write("D | " + status + " | " + d.getDescription()
                            + " | " + d.getBy());
                } else if (task instanceof Event e) {
                    writer.write("E | " + status + " | " + e.getDescription()
                            + " | " + e.getFrom() + " | " + e.getTo());
                }

                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving file.");
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

            if (fromIdx == -1 || toIdx == -1) {
                throw new InvalidFormatException(
                        "Event format: description /from START /to END");
            }

            if (fromIdx >= toIdx) {
                throw new InvalidFormatException(
                        "Event format must be: description /from START /to END");
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

    public static void printDescription() {
        System.out.println(" " + tasks.get(tasks.size() - 1));

        if (tasks.size() == 1) {
            System.out.println("Now you have 1 task in the list.");
        } else {
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        }
    }
}
