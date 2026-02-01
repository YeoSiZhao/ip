public class Deadline extends Task{

    private String by;
    public Deadline(String deadlineDescription, String by) {
        super(deadlineDescription);
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s(by: %s)",
                getType(), getIsDone(), description, by);
    }

    protected String getType() {
        return "D";
    }
}
