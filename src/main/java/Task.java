public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getIsDone() {
        return (isDone ? "X" : " "); // mark done task with X
    }
    public String getDescription() {
        return description;
    }
    public void setIsDone() {
        isDone = true;
    }
    public void setIsNotDone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s",
                getType(), getIsDone(), description);
    }

    protected String getType() {
        return " ";
    }
}


