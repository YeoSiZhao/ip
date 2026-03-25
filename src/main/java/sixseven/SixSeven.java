package sixseven;

import java.util.Scanner;
import java.util.ArrayList;

import sixseven.errors.ErrorChecker;
import sixseven.errors.SixSevenException;
import sixseven.storage.Storage;
import sixseven.task.Task;
import sixseven.utils.CommandHandler;

/**
 * Represents the main entry point of the SixSeven application.
 * A <code>SixSeven</code> object manages user interaction, command parsing,
 * task storage, and command execution in a CLI environment.
 */
public class SixSeven {

    /** Name of the chatbot displayed to the user. */
    private static final String BOT_NAME = "SixSeven";

    /** In-memory list of tasks managed during runtime. */
    private static final ArrayList<Task> tasks = new ArrayList<>();

    /** File path used for persistent storage of tasks. */
    private static final String FILE_PATH = "data/sixseven.txt";

    /** Storage handler responsible for loading and saving tasks. */
    private static final Storage storage = new Storage(FILE_PATH);

    /**
     * Starts the SixSeven application.
     * Loads tasks from storage and continuously processes user input.
     *
     * @param args Command-line arguments (not used).
     */
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

    /**
     * Returns the parsed command and description from the next input line.
     * If no description is provided, an empty string is returned.
     *
     * @param scanner Scanner used to read user input.
     * @return Result containing command and description.
     */
    private static Result getResult(Scanner scanner) {
        String line = scanner.nextLine();

        String[] input = line.split(" ", 2);
        String command = input[0];
        String description = input.length > 1 ? input[1] : "";
        return new Result(command, description);
    }

    /**
     * Stores a parsed user input consisting of a command and description.
     */
    private record Result(String command, String description) {
    }
}
