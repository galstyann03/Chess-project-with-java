package am.aua.chess.core;

import java.util.ArrayList;

/**
 * The <code>am.aua.chess.core.Bishop</code> class contains the behaviour of bishop pieces.
 * It extends the Piece class and provides methods for generating reachable positions
 * for a bishop on the board.
 */
public class Bishop extends Piece {
    /**
     * Constructs a new Bishop with the default color WHITE.
     */
    public Bishop() {
        this(Chess.PieceColor.WHITE);
    }

    /**
     * Constructs a new Bishop object with the specified color.
     *
     * @param pieceColor the color of the bishop piece
     */
    public Bishop(Chess.PieceColor pieceColor) {
        super(pieceColor);
    }

    /**
     * Returns a string representation of the bishop piece.
     * For a white bishop, it returns "B", and for a black bishop, it returns "b".
     *
     * @return the string representation of the bishop piece
     */
    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            return "B";
        else
            return "b";
    }

    /**
     * Returns an array of positions representing all possible destinations for the bishop piece.
     * It calculates the reachable positions based on the current position and the state of the chess game.
     *
     * @param chess the ongoing Chess game
     * @param p the position of the bishop on the chessboard
     * @param includeDefending a flag indicating whether to include positions where the bishop can defend its own king
     * @return an array of reachable positions for the bishop piece
     */
    public Position[] allDestinations(Chess chess, Position p, boolean includeDefending) {
        return Bishop.reachablePositions(chess, p, includeDefending);
    }

    /**
     * A static method that calculates and returns an array of positions representing all possible destinations for the bishop piece.
     * This method computes reachable positions based on the current position and the state of the chess game.
     * It considers diagonal movements until encountering another piece or reaching the edge of the board.
     *
     * @param chess the ongoing Chess game
     * @param p the position of the bishop on the chessboard
     * @param includeDefending a flag indicating whether to include positions where the bishop can defend its own king
     * @return an array of reachable positions for the bishop piece
     */
    static Position[] reachablePositions(Chess chess, Position p, boolean includeDefending) {
        ArrayList<Position> result = new ArrayList<>();

        int[] rankOffsets = {1, -1, 1, -1};
        int[] fileOffsets = {1, 1, -1, -1};

        for (int d = 0; d < 4; d++) {
            int i = p.getRank() + rankOffsets[d];
            int j = p.getFile() + fileOffsets[d];

            while(i >= 0 && i < Chess.BOARD_RANKS && j >= 0 && j < Chess.BOARD_FILES) {
                Position current = Position.generateFromRankAndFile(i, j);

                if (chess.isEmpty(current)) {
                    result.add(current);
                } else {
                    if (includeDefending || chess.getPieceAt(current).getPieceColor() != chess.getPieceAt(p).getPieceColor()) {
                        result.add(current);
                    }
                    break;
                }
                i += rankOffsets[d];
                j += fileOffsets[d];
            }
        }
        return result.toArray(new Position[]{});
    }
}