package am.aua.chess.exceptions;

/**
 * An exception thrown when an invalid arrangement of pieces is encountered.
 */
public class IllegalArrangementException extends Exception{
    /**
     * Constructs an IllegalArrangementException with a message "Illegal Arrangement".
     */
    public IllegalArrangementException() {
        super("The string does not represent a valid arrangement of pieces on a board.");
    }

    /**
     * Constructs an IllegalArrangementException with the specified message.
     *
     * @param message the message
     */
    public IllegalArrangementException(String message) {
        super(message);
    }
}
