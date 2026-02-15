package task;

public class Event extends Task {

    private String from;
    private String to;

    public Event(String eventDescription, String from, String to) {
        super(eventDescription);
        this.from = from;
        this.to = to;

    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return String.format("[E][%s] %s (from: %s to %s)", markString(), description, from, to);
    }
}