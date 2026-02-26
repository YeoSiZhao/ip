package errors;

import java.util.ArrayList;

import task.Task;

/**
 * Performs validation checks on user commands before execution.
 * An <code>ErrorChecker</code> object ensures that command formats,
 * required arguments, and task index constraints are valid.
 */
public class ErrorChecker {

    /**
     * Validates the given command and its description.
     * Throws an exception if the input format or arguments are invalid.
     *
     * @param command     Command keyword entered by the user.
     * @param description Description or arguments associated with the command.
     * @param tasks       Current list of tasks for validation checks.
     * @throws SixSevenException If validation fails.
     */
    public static void checkErrors(String command, String description, ArrayList<Task> tasks)
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
}