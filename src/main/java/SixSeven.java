import java.util.Scanner;

public class SixSeven {

    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;

    public static void main(String[] args) {
        String name = "SixSeven";
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line;
            line = scanner.nextLine();

            String[] input = line.split(" ");
            String firstWord = input[0];

            switch (firstWord) {
            case "bye":
                System.out.println("Bye. Hope to see you again soon!");
                scanner.close();
                return;
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
                addTask(line);
                break;
            }
        }
    }

    public static void addTask(String taskInfo) {
        if (taskInfo == null || taskInfo.isEmpty()) {
            System.out.println("Invalid string. Try again");
            return;
        }
        if (taskCount < 100) {
            tasks[taskCount] = new Task(taskInfo);
            taskCount++;
            System.out.println("added: " + taskInfo);
        } else {
            System.out.println("Max capacity has reached");
        }
    }

    public static void listTask() {
        if (taskCount == 0) {
            System.out.println("No task available");
        } else {
            for (int i = 0; i < taskCount; i++) {
                System.out.println("[" + tasks[i].getStatusIcon() + "] " + tasks[i].getDescription());
            }
        }
    }
    public static void markTask(String input) {
        int numberToMark = Integer.parseInt(input)-1;
        tasks[numberToMark].setIsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("[" + tasks[numberToMark].getStatusIcon() + "] " + tasks[numberToMark].getDescription());
    }
    public static void unmarkTask(String input) {
        int numberToUnmark = Integer.parseInt(input)-1;
        tasks[numberToUnmark].setIsNotDone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("[" + tasks[numberToUnmark].getStatusIcon() + "] " + tasks[numberToUnmark].getDescription());
    }
}

