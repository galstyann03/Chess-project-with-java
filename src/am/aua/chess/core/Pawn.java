package am.aua.chess.core;

import java.util.ArrayList;

/**
 * The <code>am.aua.chess.core.Pawn</code> class contains the behaviour of pawn pieces.
 * It extends the Piece class and provides methods for generating reachable positions
 * for a pawn on the board.
 */
public class Pawn extends Piece {
    /**
     * Constructs a new Pawn with the default color WHITE.
     */
    public Pawn() {
        this(Chess.PieceColor.WHITE);
    }

    /**
     * Constructs a new Pawn object with the specified color.
     *
     * @param pieceColor the color of the pawn piece
     */
    public Pawn(Chess.PieceColor pieceColor) {
        super(pieceColor);
    }

    /**
     * Returns a string representation of the pawn piece.
     * For a white pawn, it returns "P", and for a black pawn, it returns "p".
     *
     * @return the string representation of the pawn piece
     */
    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            return "P";
        else
            return "p";
    }

    /**
     * Returns an array of positions representing all possible destinations for the pawn piece.
     * It calculates the reachable positions based on the current position and the state of the chess game.
     *
     * @param chess the ongoing Chess game
     * @param p the position of the pawn on the chessboard
     * @param includeDefending a flag indicating whether to include positions where the pawn can defend its own king
     * @return an array of reachable positions for the pawn piece
     */
    public Position[] allDestinations(Chess chess, Position p, boolean includeDefending) {
        ArrayList<Position> result = new ArrayList<>();

        int rank = p.getRank();
        int file = p.getFile();
        int direction;
        if (this.getPieceColor() == Chess.PieceColor.WHITE) direction = -1;
        else direction = 1;

        Position singleStep = Position.generateFromRankAndFile(rank + direction, file);
        if (singleStep != null && chess.isEmpty(singleStep)) {
            result.add(singleStep);

            if ((this.getPieceColor() == Chess.PieceColor.WHITE && rank == Chess.WHITE_PAWN_STARTING_RANK) || (this.getPieceColor() == Chess.PieceColor.BLACK && rank == Chess.BLACK_PAWN_STARTING_RANK)) {
                Position doubleStep = Position.generateFromRankAndFile(rank + 2 * direction, file);
                if (doubleStep != null && chess.isEmpty(doubleStep)) {
                    result.add(doubleStep);
                }
            }
        }

        Position diagonalLeft = Position.generateFromRankAndFile(rank + direction, file - 1);
        Position diagonalRight = Position.generateFromRankAndFile(rank + direction, file + 1);
        if (diagonalLeft != null && !chess.isEmpty(diagonalLeft) && (includeDefending || chess.getPieceAt(diagonalLeft).getPieceColor() != this.getPieceColor())) {
            result.add(diagonalLeft);
        }
        if (diagonalRight != null && !chess.isEmpty(diagonalRight) && (includeDefending || chess.getPieceAt(diagonalRight).getPieceColor() != this.getPieceColor())) {
            result.add(diagonalRight);
        }

        return result.toArray(new Position[]{});
    }
}
