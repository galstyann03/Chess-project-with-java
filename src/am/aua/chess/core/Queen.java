package am.aua.chess.core;

import java.util.ArrayList;

/**
 * The <code>am.aua.chess.core.Queen</code> class contains the behaviour of queen pieces.
 * It extends the Piece class and provides methods for generating reachable positions
 * for a queen on the board.
 */
public class Queen extends Piece {
    /**
     * Constructs a new Queen with the default color WHITE.
     */
    public Queen() {
        this(Chess.PieceColor.WHITE);
    }

    /**
     * Constructs a new Queen object with the specified color.
     *
     * @param pieceColor the color of the queen piece
     */
    public Queen(Chess.PieceColor pieceColor) {
        super(pieceColor);
    }

    /**
     * Returns a string representation of the queen piece.
     * For a white queen, it returns "Q", and for a black queen, it returns "q".
     *
     * @return the string representation of the queen piece
     */
    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            return "Q";
        else
            return "q";
    }

    /**
     * Returns an array of positions representing all possible destinations for the queen piece.
     * It calculates the reachable positions based on the current position and the state of the chess game.
     *
     * @param chess the ongoing Chess game
     * @param p the position of the queen on the chessboard
     * @param includeDefending a flag indicating whether to include positions where the queen can defend its own king
     * @return an array of reachable positions for the queen piece
     */
    public Position[] allDestinations(Chess chess, Position p, boolean includeDefending) {
        ArrayList<Position> result = new ArrayList<>();

        Position[] rookPositions = Rook.reachablePositions(chess, p, includeDefending);
        for (Position rp : rookPositions) result.add(rp);

        Position[] bishopPositions = Bishop.reachablePositions(chess, p, includeDefending);
        for (Position bp : bishopPositions) result.add(bp);

        return result.toArray(new Position[]{});
    }
}