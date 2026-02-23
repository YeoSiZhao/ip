package utils;

import java.util.ArrayList;

import errors.SixSevenException;
import errors.UnknownCommandException;
import task.*;
import storage.Storage;

public class CommandHandler {

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
            Helpers.printAddDescription(tasks);
            storage.save(tasks);
            break;

        case "deadline":
            int byIdx = description.indexOf(" /by ");
            String by = description.substring(byIdx + 5).trim();
            String deadlineDescription = description.substring(0, byIdx);

            tasks.add(new Deadline(deadlineDescription, by));
            Helpers.printAddDescription(tasks);
            storage.save(tasks);
            break;

        case "event":
            int fromIdx = description.indexOf(" /from ");
            int toIdx = description.indexOf(" /to ");

            String eventDescription = description.substring(0, fromIdx).trim();
            String from = description.substring(fromIdx + 7, toIdx).trim();
            String to = description.substring(toIdx + 5).trim();

            tasks.add(new Event(eventDescription, from, to));
            Helpers.printAddDescription(tasks);
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
            TaskHelpers.listTask(tasks);
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