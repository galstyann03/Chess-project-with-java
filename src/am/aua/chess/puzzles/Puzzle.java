package am.aua.chess.puzzles;

import am.aua.chess.core.Chess;
import am.aua.chess.exceptions.MalformedPuzzleException;

/**
 * The Puzzle class represents a chess puzzle, containing information about the arrangement,
 * turn, difficulty level, and description.
 */
public final class Puzzle implements Comparable<Puzzle>{
    /**
     * Enumeration representing the difficulty levels of puzzles.
     */
    public enum Difficulty {
        EASY, MEDIUM, HARD, UNSPECIFIED
    }

    // a 64-character string arrangement of pieces on the board;
    private String arrangement;

    // a turn expressed as a Chess.PieceColor;
    private Chess.PieceColor turn;

    // a difficulty level
    private Difficulty difficulty;

    // a string description that doesn't contain any line breaks
    private String description;

    /**
     * Constructs a Puzzle object from a textual representation of the puzzle and its description.
     *
     * @param details A string representing the arrangement, turn, and difficulty of the puzzle
     * @param description The description of the puzzle
     * @throws MalformedPuzzleException If the puzzle representation is malformed or the arrangement is invalid
     */
    public Puzzle (String details, String description) throws MalformedPuzzleException {
        this.description = description;

        try {
            String[] components = details.split(",");
            Chess.verifyArrangement(components[0]);
            this.arrangement = components[0];
            this.turn = Chess.PieceColor.valueOf(components[1]);
            this.difficulty = Difficulty.valueOf(components[2]);
        } catch (Exception e) {
            throw new MalformedPuzzleException();
        }
    }

    /**
     * Copy constructor for creating a Puzzle object from another Puzzle object.
     *
     * @param other The Puzzle object to copy
     */
    public Puzzle(Puzzle other) {
        this.difficulty = other.difficulty;
        this.turn = other.turn;
        this.description = other.description;
        this.arrangement = other.arrangement;
    }

    /**
     * Accessor method for the arrangement of pieces on the puzzle board.
     *
     * @return The arrangement of pieces
     */
    public String getArrangement () {
        return arrangement;
    }

    /**
     * Accessor method for the turn of the puzzle.
     *
     * @return The turn of the puzzle
     */
    public Chess.PieceColor getTurn () {
        return turn;
    }

    /**
     * Accessor method for the difficulty level of the puzzle.
     *
     * @return The difficulty level of the puzzle
     */
    public Difficulty getDifficultyLevel() {
        return difficulty;
    }

    /**
     * Accessor method for the description of the puzzle.
     *
     * @return The description of the puzzle
     */
    public String getDescription() {
        return description;
    }

    /**
     * Generates the string representation of the puzzle.
     *
     * @return The string representation of the puzzle
     */
    @Override
    public String toString() {
        return getArrangement() + "," + getTurn() + "," + getDifficultyLevel() + "\n" + getDescription();
    }

    /**
     * Checks whether this Puzzle object is equal to another object.
     *
     * @param other The other object
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != Puzzle.class) return false;
            Puzzle otherPuzzle = (Puzzle) other;
            return (getArrangement().equals(otherPuzzle.getArrangement()) &&
                    getTurn() == otherPuzzle.getTurn() &&
                    getDifficultyLevel() == otherPuzzle.getDifficultyLevel());
    }

    /**
     * Compares this puzzle with another puzzle for ordering.
     * The comparison is based on the following:
     * (a) If the difficulties of two puzzles are different, then the easier one comes first.
     * (b) Otherwise, if the turns of the two puzzles are different, then the one with white’s turn comes first.
     * (c) Otherwise, if the arrangements of the two puzzles are different, then the one with lexicographically smaller arrangement comes first.
     * This method returns a negative integer, zero, or a positive integer as this puzzle is less than, equal to, or greater than the specified puzzle.
     *
     * @param other the puzzle to be compared
     * @return a negative integer, zero, or a positive integer as this puzzle is less than, equal to, or greater than the specified puzzle
     */
    public int compareTo(Puzzle other) {
        if (this.difficulty != other.difficulty)
            return this.difficulty.ordinal() - other.difficulty.ordinal();
        if (this.turn != other.turn)
            return this.turn.ordinal() - other.turn.ordinal();
        return this.arrangement.compareTo(other.arrangement);
    }
}
