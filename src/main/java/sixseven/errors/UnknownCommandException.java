package sixseven.errors;

/**
 * Represents an error caused by an unrecognized user command.
 */
public class UnknownCommandException extends SixSevenException {

    /**
     * Creates an unknown command exception with the given message.
     *
     * @param message Error message describing the unknown command.
     */
    public UnknownCommandException(String message) {
        super(message);
    }
}
