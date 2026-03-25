package sixseven.utils;

import java.util.ArrayList;

import sixseven.errors.SixSevenException;
import sixseven.errors.UnknownCommandException;
import sixseven.storage.Storage;
import sixseven.task.Deadline;
import sixseven.task.Event;
import sixseven.task.Task;
import sixseven.task.TaskHelpers;
import sixseven.task.Todo;

/**
 * Handles execution of user commands.
 * A <code>CommandHandler</code> object processes parsed commands
 * and performs the corresponding operations on the task list.
 */
public class CommandHandler {

    /**
     * Executes the specified command using the given description and task list.
     * Updates storage when the task list changes.
     *
     * @param command     Command keyword entered by the user.
     * @param description Description or arguments associated with the command.
     * @param tasks       Current list of tasks in memory.
     * @param storage     Storage handler used to persist task updates.
     * @throws SixSevenException If the command is invalid or execution fails.
     */
    public static void execute(
            String command,
            String description,
            ArrayList<Task> tasks,
            Storage storage
    ) throws SixSevenException {

        switch (command) {

        case "bye":
            System.out.println("Bye. Hope to see you again soon!");
            System.exit(0);
            break;

        case "todo":
            tasks.add(new Todo(description));
            Helpers.printAddedTask(tasks);
            storage.save(tasks);
            break;

        case "deadline":
            int byIdx = description.indexOf(" /by ");
            String by = description.substring(byIdx + 5).trim();
            String deadlineDescription = description.substring(0, byIdx);

            tasks.add(new Deadline(deadlineDescription, by));
            Helpers.printAddedTask(tasks);
            storage.save(tasks);
            break;

        case "event":
            int fromIdx = description.indexOf(" /from ");
            int toIdx = description.indexOf(" /to ");

            String eventDescription = description.substring(0, fromIdx).trim();
            String from = description.substring(fromIdx + 7, toIdx).trim();
            String to = description.substring(toIdx + 5).trim();

            tasks.add(new Event(eventDescription, from, to));
            Helpers.printAddedTask(tasks);
            storage.save(tasks);
            break;

        case "delete":
            int deleteIndex = Integer.parseInt(description) - 1;
            Task removedTask = tasks.remove(deleteIndex);

            System.out.println("Noted. I've removed this task:");
            System.out.println(" " + removedTask);
            Helpers.printTaskCountAfterUpdate(tasks);
            storage.save(tasks);
            break;

        case "find":
            TaskHelpers.findTasks(description, tasks);
            break;

        case "list":
            TaskHelpers.listTasks(tasks);
            break;

        case "mark":
            TaskHelpers.markTask(description, tasks);
            storage.save(tasks);
            break;

        case "unmark":
            TaskHelpers.unmarkTask(description, tasks);
            storage.save(tasks);
            break;

        default:
            throw new UnknownCommandException("What do you mean!!!! >:(");
        }
    }
}
