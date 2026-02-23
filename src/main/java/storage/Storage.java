package storage;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;

public class Storage {

    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File file = new File(filePath);

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" \\| ");

                if (parts.length < 3) continue;

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
            }

            scanner.close();

        } catch (IOException e) {
            System.out.println("Error loading file.");
        }

        return tasks;
    }

    public void save(ArrayList<Task> tasks) {
        try {
            FileWriter fw = new FileWriter(filePath);

            for (Task task : tasks) {

                String status = task.getIsDone() ? "1" : "0";
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
