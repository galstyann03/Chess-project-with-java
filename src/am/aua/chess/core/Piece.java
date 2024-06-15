package am.aua.chess.core;

import am.aua.chess.core.Chess.PieceColor;

/**
 * The abstract Piece class represents a chess piece with a color.
 */
public abstract class Piece implements Cloneable {
    private PieceColor pieceColor; // Instance variable to store the color of the piece

    /**
     * Constructs a new Piece object with PieceColor.White
     */
    public Piece() {
        this(PieceColor.WHITE);
    }

    /**
     * Constructs a new Piece object with the specified color.
     *
     * @param pieceColor the color of the piece
     */
    public Piece(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    /**
     * Gets the color of the piece.
     *d
     * @return the color of the piece
     */
    public final PieceColor getPieceColor() {
        return this.pieceColor;
    }

    /**
     * Returns a set of all positions into which a piece might perform a
     * valid move from the given position in the ongoing game.
     * This is an abstract method and should be implemented by concrete subclasses.
     *
     * @param chess the ongoing Chess game
     * @param p the position of the piece on the board
     * @param includeDefending a flag indicating whether to include positions where the piece can defend its own king
     * @return an array of reachable positions for the piece
     */
    public abstract Position[] allDestinations(Chess chess, Position p, boolean includeDefending);

    /**
     * Creates and returns a deep copy of the current Piece object.
     *
     * @return A new Piece object that is a deep copy of the current object.
     */
    @Override
    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
