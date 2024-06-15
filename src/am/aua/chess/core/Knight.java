package am.aua.chess.core;

import java.util.ArrayList;

/**
 * The <code>am.aua.chess.core.Knight</code> class contains the behaviour of knight pieces.
 * It extends the Piece class and provides methods for generating reachable positions
 * for a knight on the board.
 */
public class Knight extends Piece {
    /**
     * Constructs a new Knight with the default color WHITE.
     */
    public Knight() {
        this(Chess.PieceColor.WHITE);
    }

    /**
     * Constructs a new Knight with the specified color.
     *
     * @param color the color of the Knight (WHITE or BLACK)
     */
    public Knight(Chess.PieceColor color) {
        super(color);
    }

    /**
     * Returns a string representation of the knight piece.
     * White knight is represented by "N" and black knight is represented by "n".
     *
     * @return the string representation of the knight piece
     */
    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            return "N";
        else
            return "n";
    }
    /**
     * Generates and returns a set of all the positions into which a knight
     * might perform a valid move from a given position in the given ongoing
     * chess game.
     *
     * @param chess the ongoing <code>am.aua.chess.core.Chess</code> game
     * @param p the position of the knight
     * @param includeDefending a flag indicating whether to include positions where the knight can defend its own king
     * @return a <code>am.aua.chess.core.Position</code> array with all the positions
     * that a knight can move into from position <code>p</code>
     */
    public Position[] allDestinations(Chess chess, Position p, boolean includeDefending) {
        int[][] pattern = {
                {p.getRank() + 2, p.getFile() + 1},
                {p.getRank() + 2, p.getFile() - 1},
                {p.getRank() - 2, p.getFile() + 1},
                {p.getRank() - 2, p.getFile() - 1},

                {p.getRank() + 1, p.getFile() + 2},
                {p.getRank() + 1, p.getFile() - 2},
                {p.getRank() - 1, p.getFile() + 2},
                {p.getRank() - 1, p.getFile() - 2}
        };

        ArrayList<Position> result = new ArrayList<>();

        for (int i = 0; i < pattern.length; i++) {
            Position potential = Position.generateFromRankAndFile(pattern[i][0],
                    pattern[i][1]);
            if (potential != null &&
                    (chess.isEmpty(potential) ||
                            chess.getPieceAt(potential).getPieceColor() != chess.getPieceAt(p).getPieceColor()
                    )
            )
                result.add(potential);
        }

        return result.toArray(new Position[]{});
    }
}
