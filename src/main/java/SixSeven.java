import java.util.Scanner;

public class SixSeven {

    public static final int MAX_TASK = 100;
    public static final String BOT_NAME = "SixSeven";
    private static final Task[] tasks = new Task[MAX_TASK];
    private static int taskCount = 0;

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
                    tasks[taskCount] = new Todo(description);
                    printDescription();
                    break;
                case "deadline":
                    int byIdx = description.indexOf("/by");

                    String by = description.substring(byIdx + 4).trim();
                    String deadlineDescription = description.substring(0, byIdx);

                    tasks[taskCount] = new Deadline(deadlineDescription, by);
                    printDescription();
                    break;
                case "event":
                    int fromIdx = description.indexOf("/from");
                    int toIdx = description.indexOf("/to");

                    String eventDescription = description.substring(0, fromIdx).trim();
                    String from = description.substring(fromIdx + 6, toIdx).trim();
                    String to = description.substring(toIdx + 4).trim();

                    tasks[taskCount] = new Event(eventDescription, from, to);
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

            if (taskIdx < 1 || taskIdx > taskCount) {
                throw new InvalidFormatException("Task number is out of range.");
            }

            int internalIdx = taskIdx - 1;

            if (command.equals("unmark") && !tasks[internalIdx].getIsDone()) {
                throw new InvalidFormatException("Task " + description + " is not done yet.");
            }
            if (command.equals("mark") && tasks[internalIdx].getIsDone()) {
                throw new InvalidFormatException("Task " + description + " is already done.");
            }
        }
    }


    public static void listTask() {
        if (taskCount == 0) {
            System.out.println("No task available");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println(i + 1 + "." + tasks[i]);
            }
        }
    }

    public static void markTask(String input) {
        int numberToMark = Integer.parseInt(input) - 1;
        tasks[numberToMark].setIsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("[" + tasks[numberToMark].markString() + "] " + tasks[numberToMark].getDescription());
    }

    public static void unmarkTask(String input) {
        int numberToUnmark = Integer.parseInt(input) - 1;
        tasks[numberToUnmark].setIsNotDone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("[" + tasks[numberToUnmark].markString() + "] " + tasks[numberToUnmark].getDescription());
    }

    public static void printDescription() {
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + tasks[taskCount]);
        taskCount++;
        String strTask;
        if (taskCount == 1) {
            strTask = "task";
        } else {
            strTask = "tasks";
        }
        System.out.println("Now you have " + taskCount + " " + strTask + " in the list.");
    }
}

