package sixseven.task;

/**
 * Represents a basic task with only a description and completion status.
 * A <code>Todo</code> does not include any date or time information.
 */
public class Todo extends Task {

    /**
     * Creates a todo task with the given description.
     *
     * @param description Description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }
}
