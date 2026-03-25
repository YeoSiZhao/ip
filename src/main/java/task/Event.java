package task;

/**
 * Represents an event task with a start and end time.
 * An <code>Event</code> stores the event description together with
 * the period during which the event takes place.
 */
public class Event extends Task {

    private String from;
    private String to;

    /**
     * Creates an event with the given description, start time, and end time.
     *
     * @param eventDescription Description of the event.
     * @param from Start time or starting detail of the event.
     * @param to End time or ending detail of the event.
     */
    public Event(String eventDescription, String from, String to) {
        super(eventDescription);
        this.from = from;
        this.to = to;

    }

    /**
     * Returns the start time or starting detail of the event.
     *
     * @return Event start information.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the end time or ending detail of the event.
     *
     * @return Event end information.
     */
    public String getTo() {
        return to;
    }

    /**
     * Returns the string representation of the event.
     *
     * @return Formatted event string.
     */
    @Override
    public String toString() {
        return String.format("[E][%s] %s (from: %s to %s)", markString(), description, from, to);
    }
}
