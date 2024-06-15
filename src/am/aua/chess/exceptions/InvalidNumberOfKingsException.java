package am.aua.chess.exceptions;

/**
 * An exception thrown when the number of kings in the arrangement is invalid.
 */
public class InvalidNumberOfKingsException extends IllegalArrangementException{
    /**
     * Constructs an InvalidNumberOfKingsException with a message "Invalid number of kings:
     * there should be exactly one white and exactly one black king on the board.".
     */
    public InvalidNumberOfKingsException() {
        super("In a game of classic chess, there has to be exactly one king of each color.");
    }

    /**
     * Constructs an InvalidNumberOfKingsException with the specified message.
     *
     * @param message the message
     */
    public InvalidNumberOfKingsException(String message) {
        super(message);
    }
}
