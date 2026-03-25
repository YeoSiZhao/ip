package errors;

/**
 * Represents the base checked exception type for the SixSeven application.
 * Specific user-facing error conditions extend this class.
 */
public class SixSevenException extends Exception {

    /**
     * Creates a SixSeven exception with the given message.
     *
     * @param message Error message describing the failure.
     */
    public SixSevenException(String message) {
        super(message);
    }
}
