package task;

public class Deadline extends Task {

    private String by;
    public Deadline(String deadlineDescription, String by) {
        super(deadlineDescription);
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[D][%s] %s(by: %s)", markString(), description, by);
    }
}
