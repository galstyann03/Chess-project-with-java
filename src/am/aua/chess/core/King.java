package am.aua.chess.core;

import java.util.ArrayList;

/**
 * The King class represents a king chess piece.
 * It extends the Piece class and provides methods for generating reachable positions
 * for a king on the chessboard.
 */
public class King extends Piece {
    // Instance variable to track whether the king has moved
    private boolean hasMoved;

    /**
     * Constructs a new King object with the default color WHITE.
     */
    public King() {
        this(Chess.PieceColor.WHITE);
    }

    /**
     * Constructs a new King object with the specified color and sets the hasMoved flag to false.
     *
     * @param pieceColor the color of the king piece
     */
    public King(Chess.PieceColor pieceColor) {
        this(pieceColor, false);
    }

    /**
     * Constructs a new King object with the specified color and hasMoved status.
     *
     * @param pieceColor the color of the king piece
     * @param hasMoved true if the king has moved previously, false otherwise
     */
    public King(Chess.PieceColor pieceColor, boolean hasMoved) {
        super(pieceColor);
        this.hasMoved = hasMoved;
    }

    /**
     * Returns whether the king has moved from its initial position.
     *
     * @return true if the king has moved, false otherwise
     */
    public boolean getHasMoved() {
        return this.hasMoved;
    }

    /**
     * Sets whether the king has moved from its initial position.
     *
     * @param hasMoved true if the king has moved, false otherwise
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Returns a string representation of the king piece.
     * For a white king that has moved, it returns "L".
     * For a white king that hasn't moved, it returns "K".
     * For a black king that has moved, it returns "l".
     * For a black king that hasn't moved, it returns "k".
     *
     * @return the string representation of the king piece
     */
    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            if (this.hasMoved)
                return "L";
            else
                return "K";
        else
            if (this.hasMoved)
            return "l";
        else
            return "k";
    }

    /**
     * Generates a set of potential destinations for a king based on its pattern of movement.
     * This method is used internally by the allDestinations method.
     *
     * @param chess the ongoing Chess game
     * @param p the position of the king on the board
     * @param includeDefending a flag indicating whether to include positions where the king can defend its own king
     * @return an array of potential destinations for the king
     */
    private Position[] kingPattern(Chess chess, Position p, boolean includeDefending) {
        //An object of type King can access a private method of another King object.
        //The separation of patterns and accessible squares eliminates an infinite recursion
        //between kings, asking each other about their reachable squares.
        ArrayList<Position> result = new ArrayList<>();

        int[][] pattern = {
                {p.getRank() - 1, p.getFile() - 1},
                {p.getRank(),     p.getFile() - 1},
                {p.getRank() + 1, p.getFile() - 1},

                {p.getRank() - 1, p.getFile()},
                {p.getRank() + 1, p.getFile()},

                {p.getRank() - 1, p.getFile() + 1},
                {p.getRank(),     p.getFile() + 1},
                {p.getRank() + 1, p.getFile() + 1}
        };

        for (int i = 0; i < pattern.length; i++) {
            Position potential = Position.generateFromRankAndFile(pattern[i][0], pattern[i][1]);
            if (potential != null && (chess.isEmpty(potential) || includeDefending
                    || chess.getPieceAt(potential).getPieceColor()
                    != chess.getPieceAt(p).getPieceColor()))
                result.add(potential);
        }

        return result.toArray(new Position[]{});
    }

    /**
     * Generates reachable positions for a king piece on the chessboard, including or excluding defensive positions.
     * This method overrides the abstract method in the Piece class.
     *
     * @param chess the ongoing Chess game
     * @param p the position of the king on the board
     * @param includeDefending a flag indicating whether to include positions where the king can defend its own king
     * @return an array of reachable positions for the king
     */
    @Override
    public Position[] allDestinations(Chess chess, Position p, boolean includeDefending) {

        //This updated allDestinations method eliminates threatened squares from the reachable set.
        // Castling not implemented.

        ArrayList<Position> result = new ArrayList<>();
        ArrayList<Position> opponentReachable = new ArrayList<>();
        Position[] potentials = this.kingPattern(chess, p, false);


        Piece[][] board = chess.getBoard();
        for (int i = 0; i < Chess.BOARD_RANKS; i++)
            for (int j = 0; j < Chess.BOARD_FILES; j++)
                if (board[i][j] != null &&
                        board[i][j].getPieceColor() == chess.invertColor(this.getPieceColor())
                ) {
                    Position[] opponentPiecePattern;
                    if (board[i][j] instanceof King){
                        opponentPiecePattern = this.kingPattern(chess,
                                Position.generateFromRankAndFile(i, j), true);

                    } else {
                        opponentPiecePattern = board[i][j].allDestinations(chess,
                                Position.generateFromRankAndFile(i, j), true);
                    }
                    for (int k = 0; k < opponentPiecePattern.length; k++)
                        opponentReachable.add(opponentPiecePattern[k]);
                }

        if (!this.hasMoved) {
            Position path1, path2, path3, path4, kingLocation;

            if (this.getPieceColor() == Chess.PieceColor.WHITE) {
                path1 = Position.generateFromRankAndFile(7, 2);
                path2 = Position.generateFromRankAndFile(7, 3);
                kingLocation = Position.generateFromRankAndFile(7, 4);
                path3 = Position.generateFromRankAndFile(7, 5);
                path4 = Position.generateFromRankAndFile(7, 6);

                if (board[7][0] != null
                        && board[7][0] instanceof Rook
                        && !((Rook)board[7][0]).getHasMoved()
                        && !opponentReachable.contains(path1)
                        && chess.isEmpty(path1)
                        && !opponentReachable.contains(path2)
                        && chess.isEmpty(path2)
                        && !opponentReachable.contains(kingLocation)
                ) result.add(path1);

                if (board[7][7] != null
                        && board[7][7] instanceof Rook
                        && !((Rook)board[7][7]).getHasMoved()
                        && !opponentReachable.contains(path3)
                        && chess.isEmpty(path3)
                        && !opponentReachable.contains(path4)
                        && chess.isEmpty(path4)
                        && !opponentReachable.contains(kingLocation)
                ) result.add(path4);

            } else {
                path1 = Position.generateFromRankAndFile(0,2);
                path2 = Position.generateFromRankAndFile(0,3);
                kingLocation = Position.generateFromRankAndFile(0,4);
                path3 = Position.generateFromRankAndFile(0,5);
                path4 = Position.generateFromRankAndFile(0,6);

                if (board[0][0] != null
                        && board[0][0] instanceof Rook
                        && !((Rook)board[0][0]).getHasMoved()
                        && !opponentReachable.contains(path1)
                        && chess.isEmpty(path1)
                        && !opponentReachable.contains(path2)
                        && chess.isEmpty(path2)
                        && !opponentReachable.contains(kingLocation)
                ) result.add(path1);

                if (board[0][7] != null
                        && board[0][7] instanceof Rook
                        && !((Rook)board[0][7]).getHasMoved()
                        && !opponentReachable.contains(path3)
                        && chess.isEmpty(path3)
                        && !opponentReachable.contains(path4)
                        && chess.isEmpty(path4)
                        && !opponentReachable.contains(kingLocation)
                ) result.add(path4);
            }
        }

        for (Position potential : potentials)
            if (!opponentReachable.contains(potential))
                result.add(potential);


        return result.toArray(new Position[]{});
    }
}
