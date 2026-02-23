import java.util.Scanner;
import java.util.ArrayList;

import errors.ErrorChecker;
import errors.SixSevenException;
import task.Task;

import utils.CommandHandler;
import storage.Storage;

public class SixSeven {

    public static final String BOT_NAME = "test";
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
                Result result = getResult(scanner);

                ErrorChecker.checkErrors(result.command(), result.description(), tasks);

                CommandHandler.execute(result.command(), result.description(), tasks, storage);

            } catch (SixSevenException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Result getResult(Scanner scanner) {
        String line = scanner.nextLine();

        String[] input = line.split(" ", 2);
        String command = input[0];
        String description = input.length > 1 ? input[1] : "";
        return new Result(command, description);
    }

    private record Result(String command, String description) {
    }
}