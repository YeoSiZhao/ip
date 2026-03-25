package task;

/**
 * Represents a task that must be completed by a specified deadline.
 * A <code>Deadline</code> stores the task description together with
 * the due date or time.
 */
public class Deadline extends Task {

    private String by;

    /**
     * Creates a deadline task with the given description and due date.
     *
     * @param deadlineDescription Description of the deadline task.
     * @param by Due date or due time of the task.
     */
    public Deadline(String deadlineDescription, String by) {
        super(deadlineDescription);
        this.by = by;
    }

    /**
     * Returns the due date or due time of the task.
     *
     * @return Deadline information.
     */
    public String getBy() {
        return by;
    }

    /**
     * Returns the string representation of the deadline task.
     *
     * @return Formatted deadline string.
     */
    @Override
    public String toString() {
        return String.format("[D][%s] %s (by: %s)", markString(), description, by);
    }
}
