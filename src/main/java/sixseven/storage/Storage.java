package sixseven.storage;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import sixseven.task.Deadline;
import sixseven.task.Event;
import sixseven.task.Task;
import sixseven.task.Todo;

/**
 * Handles persistent storage of tasks in a text file.
 * A <code>Storage</code> object is responsible for creating the storage file,
 * loading tasks from disk, and saving tasks back to disk.
 */
public class Storage {

    private final String filePath;

    /**
     * Creates a storage handler for the given file path.
     * The parent directory and file are created if they do not already exist.
     *
     * @param filePath Path to the task storage file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;

        try {
            File file = new File(filePath);
            File parent = file.getParentFile();

            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (IOException e) {
            System.out.println("Error initializing storage.");
        }
    }

    /**
     * Returns tasks loaded from the storage file.
     *
     * @return List of tasks loaded from disk.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" \\| ");

                if (parts.length < 3) {
                    continue;
                }

                String type = parts[0];
                boolean isDone = parts[1].equals("1");

                switch (type) {

                case "T":
                    Task todo = new Todo(parts[2]);
                    if (isDone) {
                        todo.setDone(true);
                    }
                    tasks.add(todo);
                    break;

                case "D":
                    if (parts.length < 4) {
                        continue;
                    }
                    Task deadline = new Deadline(parts[2], parts[3]);
                    if (isDone) {
                        deadline.setDone(true);
                    }
                    tasks.add(deadline);
                    break;

                case "E":
                    if (parts.length < 5) {
                        continue;
                    }
                    Task event = new Event(parts[2], parts[3], parts[4]);
                    if (isDone) {
                        event.setDone(true);
                    }
                    tasks.add(event);
                    break;

                default:
                    break;
                }
            }

            scanner.close();

        } catch (IOException e) {
            System.out.println("Error loading file.");
        }

        return tasks;
    }

    /**
     * Saves the current task list to the storage file.
     *
     * @param tasks List of tasks to be written to disk.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            FileWriter fw = new FileWriter(filePath);

            for (Task task : tasks) {

                String status = task.isDone() ? "1" : "0";
                String line = "";

                if (task instanceof Todo) {
                    line = "T | " + status + " | " + task.getDescription();
                } else if (task instanceof Deadline d) {
                    line = "D | " + status + " | " + d.getDescription()
                            + " | " + d.getBy();
                } else if (task instanceof Event e) {
                    line = "E | " + status + " | " + e.getDescription()
                            + " | " + e.getFrom() + " | " + e.getTo();
                }

                fw.write(line + System.lineSeparator());
            }

            fw.close();

        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }
}
