package am.aua.chess.core;

import java.util.ArrayList;

/**
 * The Rook class represents a rook chess piece.
 * It extends the Piece class and provides methods for generating reachable positions
 * for a rook on the chessboard.
 */
public class Rook extends Piece {
    // Instance variable to track whether the rook has moved
    private boolean hasMoved;

    /**
     * Constructs a new Rook object with the default color WHITE.
     */
    public Rook() {
        this(Chess.PieceColor.WHITE);
    }

    /**
     * Constructs a new Rook object with the specified color and hasMoved set to false.
     *
     * @param color the color of the rook piece
     */
    public Rook(Chess.PieceColor color) {
        this(color, false);
    }

    /**
     * Constructs a new Rook object with the specified color and hasMoved status.
     *
     * @param color the color of the rook piece
     * @param hasMoved true if the rook has moved previously, false otherwise
     */
    public Rook(Chess.PieceColor color, boolean hasMoved) {
        super(color);
        this.hasMoved = hasMoved;
    }

    /**
     * Returns whether the rook has moved from its initial position.
     *
     * @return true if the rook has moved, false otherwise
     */
    public boolean getHasMoved() {
        return this.hasMoved;
    }

    /**
     * Sets whether the rook has moved from its initial position.
     *
     * @param hasMoved true if the rook has moved, false otherwise
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Returns a string representation of the rook piece.
     * For a white rook that has moved, it returns "S".
     * For a white rook that hasn't moved, it returns "R".
     * For a black rook that has moved, it returns "s".
     * For a black rook that hasn't moved, it returns "r".
     *
     * @return the string representation of the rook piece
     */
    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            if (this.hasMoved)
                return "S";
            else
                return "R";
        else
        if (this.hasMoved)
            return "s";
        else
            return "r";
    }

    /**
     * Returns an array of positions representing all possible destinations for the rook piece.
     * It calculates the reachable positions based on the current position and the state of the chess game.
     *
     * @param chess the ongoing Chess game
     * @param p the position of the rook on the chessboard
     * @param includeDefending a flag indicating whether to include positions where the rook can defend its own king
     * @return an array of reachable positions for the rook piece
     */
    public Position[] allDestinations(Chess chess, Position p, boolean includeDefending) {
        return Rook.reachablePositions(chess, p, includeDefending);
    }

    /**
     * A static method that calculates and returns an array of positions representing all possible destinations for the rook piece.
     * This method computes reachable positions based on the current position and the state of the chess game.
     * It considers vertical and horizontal movements until encountering another piece or reaching the edge of the board.
     *
     * @param chess the ongoing Chess game
     * @param p the position of the rook on the chessboard
     * @param includeDefending a flag indicating whether to include positions where the rook can defend its own king
     * @return an array of reachable positions for the rook piece
     */
     static Position[] reachablePositions(Chess chess, Position p, boolean includeDefending) {
        int[] rankOffsets = {1, -1, 0, 0};
        int[] fileOffsets = {0, 0, 1, -1};

         ArrayList<Position> result = new ArrayList<>();

        for (int d = 0; d < 4; d++) {
            int i = p.getRank() + rankOffsets[d];
            int j = p.getFile() + fileOffsets[d];
            while (i >= 0 && i < Chess.BOARD_RANKS &&
                    j >= 0 && j < Chess.BOARD_FILES) {
                Position potential = Position.generateFromRankAndFile(i, j);

                if (chess.isEmpty(potential))
                    result.add(potential);
                else {
                    if (includeDefending || chess.getPieceAt(potential).getPieceColor() != chess.getPieceAt(p).getPieceColor())
                        result.add(potential);
                    break;
                }
                i += rankOffsets[d];
                j += fileOffsets[d];
            }
        }

        return result.toArray(new Position[]{});
    }
}
