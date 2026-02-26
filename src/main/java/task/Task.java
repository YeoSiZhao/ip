package task;

/**
 * Represents a generic task in the system.
 * A <code>Task</code> object contains a description and a completion status.
 */
public class Task {

    /** Description of the task. */
    protected String description;

    /** Indicates whether the task is completed. */
    protected boolean isDone;

    /**
     * Creates a Task with the specified description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the mark string representing completion status.
     *
     * @return "X" if the task is done, otherwise a blank space.
     */
    public String markString() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the description of the task.
     *
     * @return Task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks the task as completed.
     */
    public void setIsDone() {
        isDone = true;
    }

    /**
     * Returns whether the task is completed.
     *
     * @return true if task is done, false otherwise.
     */
    public boolean getIsDone() {
        return isDone;
    }

    /**
     * Marks the task as not completed.
     */
    public void setIsNotDone() {
        isDone = false;
    }

    /**
     * Returns the string representation of the task.
     *
     * @return Formatted task string.
     */
    @Override
    public String toString() {
        return String.format("[T][%s] %s", markString(), description);
    }
}