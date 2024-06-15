package am.aua.chess.exceptions;

/**
 * This exception is thrown when the arrangement of pieces on the chess board in a puzzle is invalid.
 */
public class MalformedPuzzleException extends Exception {
    /**
     * Constructs a new MalformedPuzzleException with a default message
     * "Invalid arrangement of pieces on the board.".
     */
    public MalformedPuzzleException() {
        super("Malformed Puzzle");
    }

    /**
     * Constructs a new MalformedPuzzleException with the specified message.
     *
     * @param message the message
     */
    public MalformedPuzzleException(String message) {
        super(message);
    }
}
