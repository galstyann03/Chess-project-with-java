package am.aua.chess.core;

import am.aua.chess.exceptions.IllegalArrangementException;
import am.aua.chess.exceptions.InvalidNumberOfKingsException;

import java.util.ArrayList;

/**
 * The <code>am.aua.chess.core.Chess</code> class encapsulates the state of an ongoing game of
 * chess.
 */
public class Chess implements Cloneable {
    /**
     * Enumeration representing the color of a chess piece.
     * Each color can be either WHITE or BLACK.
     */
    public enum PieceColor {
        WHITE, BLACK
    }

    /**
     * Enumeration representing the status of a chess game.
     * The game can be either ONGOING, DRAW, WHITE_WON, or BLACK_WON.
     */
    public enum GameStatus {
        ONGOING, DRAW, WHITE_WON, BLACK_WON
    }

    /**
     * The number of board ranks in chess.
     */
    public static final int BOARD_RANKS = 8;

    /**
     * The number of board files in chess.
     */
    public static final int BOARD_FILES = 8;

    /**
     * Represents the starting rank for white pawns on the chessboard.
     */
    public static final int WHITE_PAWN_STARTING_RANK = 6;

    /**
     * Represents the starting rank for black pawns on the chessboard.
     */
    public static final int BLACK_PAWN_STARTING_RANK = 1;

    private Piece[][] board; // an instance variable for the board and pieces on it
    private int numberOfMoves; // an instance variable counter for the current number of moves in the game
    private GameStatus gameStatus; // The status of the game

    /**
     * Constructs a new Chess game with the standard initial configuration.
     * The standard initial configuration places the pieces on the board in the following layout:
     * rnbqkbnr
     * pppppppp
     * --------
     * --------
     * --------
     * --------
     * PPPPPPPP
     * RNBQKBNR
     * The player with the WHITE pieces starts the game.
     *
     * @throws IllegalArrangementException if the arrangement violates the rules of chess
     */
    public Chess() throws IllegalArrangementException {
        this("rnbqkbnr" +
                        "pppppppp" +
                        "--------" +
                        "--------" +
                        "--------" +
                        "--------" +
                        "PPPPPPPP" +
                        "RNBQKBNR",
                PieceColor.WHITE);
    }

    /**
     * Constructs a new Chess game with the specified piece arrangement and current player turn.
     * The piece arrangement string should be a sequence of characters representing the positioning
     * of the different pieces on the chess board. Its length should be 64 to represent the squares
     * of the board in a row-major order. Each character represents a chess piece according to the
     * following mapping:
     * 'R'/'r' - white/black rook that hasn’t moved yet
     * 'S'/'s' - white/black rook that has moved already
     * 'N'/'n' - white/black knight
     * 'B'/'b' - white/black bishop
     * 'K'/'k' - white/black king that hasn’t moved yet
     * 'L'/'l' - white/black king that has moved already
     * 'Q'/'q' - white/black queen
     * 'P'/'p' - white/black pawn
     * The turn parameter indicates which player is about to make a move. It should be either PieceColor.WHITE or PieceColor.BLACK.
     * This constructor ensures that there is exactly one black and one white king present on the board.
     * If the arrangement violates this rule, the program aborts and prints a message.
     *
     * @param arrangement a string representing the positioning of pieces on the chess board
     * @param turn the color of the player who is about to make a move
     * @throws IllegalArrangementException if the arrangement violates the rules of chess
     */
    public Chess(String arrangement, PieceColor turn) throws IllegalArrangementException {
        verifyArrangement(arrangement);
        this.numberOfMoves = turn.ordinal();
        this.gameStatus = GameStatus.ONGOING;
        this.board = new Piece[BOARD_RANKS][BOARD_FILES];

        for (int i = 0; i < arrangement.length(); i++) {
            switch (arrangement.charAt(i)) {
                case 'R':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Rook(PieceColor.WHITE);
                    break;
                case 'r':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Rook(PieceColor.BLACK);
                    break;
                case 'S':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Rook(PieceColor.WHITE, true);
                    break;
                case 's':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Rook(PieceColor.BLACK, true);
                    break;
                case 'N':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Knight(PieceColor.WHITE);
                    break;
                case 'n':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Knight(PieceColor.BLACK);
                    break;
                case 'B':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Bishop(PieceColor.WHITE);
                    break;
                case 'b':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Bishop(PieceColor.BLACK);
                    break;
                case 'K':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new King(PieceColor.WHITE);
                    break;
                case 'k':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new King(PieceColor.BLACK);
                    break;
                case 'L':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new King(PieceColor.WHITE, true);
                    break;
                case 'l':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new King(PieceColor.BLACK, true);
                    break;
                case 'Q':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Queen(PieceColor.WHITE);
                    break;
                case 'q':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Queen(PieceColor.BLACK);
                    break;
                case 'P':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Pawn(PieceColor.WHITE);
                    break;
                case 'p':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Pawn(PieceColor.BLACK);
                    break;
            }
        }
    }

    /**
     * Returns a <code>Piece</code> matrix representing the game board and the
     * pieces on it.
     *
     * @return the board as a <code>Piece</code> matrix
     */
    public Piece[][] getBoard() {
        Piece[][] board = new Piece[BOARD_RANKS][BOARD_FILES];
        for (int i = 0; i < BOARD_RANKS; i++)
            for (int j = 0; j < BOARD_FILES; j++)
                if (this.board[i][j] != null)
                    board[i][j] = this.board[i][j].clone();
        return board;
    }

    /**
     * Gets the current turn of the player.
     *
     * @return the current turn, which can be either WHITE or Black
     */
    public PieceColor getTurn() {
        return PieceColor.values()[this.numberOfMoves % 2];
    }

    /**
     * Checks if the game is over or if it still continues.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return this.gameStatus != GameStatus.ONGOING;
    }

    /**
     * Gets the current status of the game.
     *
     * @return the current game status
     */
    public GameStatus getGameStatus() {
        return this.gameStatus;
    }

    /**
     * Checks if a given position on the board is null.
     *
     * @param p the position that should be tested
     * @return true if and only if <code>p</code> is null
     */
    public boolean isEmpty(Position p) {
        return this.board[p.getRank()][p.getFile()] == null;
    }

    /**
     * Returns the <code>Piece</code> representing the piece on a given position
     * on the board.
     *
     * @param p the position on the board
     * @return the <code>Piece</code> representing the piece on <code>p</code>
     */
    public Piece getPieceAt(Position p) {
        return this.board[p.getRank()][p.getFile()];
    }

    /**
     * Identifies and returns the set of all positions that can serve as
     * destinations for a move from the given origin.
     * If the origin is not a real object or is empty, the method just returns
     * <code>null</code>. Otherwise, it returns a listing of all eligible
     * destinations based on the type of piece in origin.
     *
     * @param origin the origin position on the board
     * @return the array with the set of reachable positions
     */
    public Position[] reachableFrom(Position origin) {
        if (origin == null || this.isEmpty(origin)) return null;
        Piece piece = getPieceAt(origin);

        return  piece.allDestinations(this, origin, false);
    }

    /**
     * Attempts to make a given <code>am.aua.chess.core.Move</code>. If the move is valid, i.e.
     * the destination is in the set reachable from the origin, the method
     * correctly updates the state of <code>am.aua.chess.core.Chess</code> and returns
     * <code>true</code>. Otherwise, the method returns <code>false</code>.
     *
     * @param m the move attempted
     * @return true if and only if the move was successfully made
     */
    public boolean performMove(Move m) {
        Position origin = m.getOrigin();
        Position destination = m.getDestination();

        if (this.getPieceAt(origin).getPieceColor() != this.getTurn())
            return  false;

        Position[] reachable = this.reachableFrom(origin);
        Piece[][] boardCopy = this.getBoard();

        for (int i = 0; i < reachable.length; i++)
            if (destination.getRank() == reachable[i].getRank()
                    && destination.getFile() == reachable[i].getFile()) {

                if (this.board[origin.getRank()][origin.getFile()] instanceof King) {
                    if (origin.getRank() == 7
                            && origin.getFile() == 4
                            && this.board[origin.getRank()][origin.getFile()].getPieceColor() == PieceColor.WHITE
                            && !((King) this.board[origin.getRank()][origin.getFile()]).getHasMoved())
                    {
                        if (destination.getRank() == 7 && destination.getFile() == 2) {
                            this.board[7][3] = new Rook(PieceColor.WHITE, true);
                            this.board[7][0] = null;
                        }
                        if (destination.getRank() == 7 && destination.getFile() == 6) {
                            this.board[7][5] = new Rook(PieceColor.WHITE, true);
                            this.board[7][7] = null;
                        }
                    }

                    if (origin.getRank() == 0
                            && origin.getFile() == 4
                            && this.board[origin.getRank()][origin.getFile()].getPieceColor() == PieceColor.BLACK
                            && !((King) this.board[origin.getRank()][origin.getFile()]).getHasMoved())
                    {
                        if (destination.getRank() == 0 && destination.getFile() == 2) {
                            this.board[0][3] = new Rook(PieceColor.BLACK, true);
                            this.board[0][0] = null;
                        }

                        if (destination.getRank() == 0 && destination.getFile() == 6) {
                            this.board[0][5] = new Rook(PieceColor.BLACK, true);
                            this.board[0][7] = null;
                        }
                    }

                    ((King) this.board[origin.getRank()][origin.getFile()]).setHasMoved(true);
                }

                if (this.board[origin.getRank()][origin.getFile()] instanceof Rook)
                    ((Rook) this.board[origin.getRank()][origin.getFile()]).setHasMoved(true);

                this.board[destination.getRank()][destination.getFile()] =
                        this.board[origin.getRank()][origin.getFile()];
                this.board[origin.getRank()][origin.getFile()] = null;

                if (isKingUnderAttack(this.getTurn())) {
                    this.board = boardCopy;
                    return false;
                }

                this.numberOfMoves++;
                updateGameStatus();
                return true;
            }

        return false;
    }

    /**
     * Determines whether the king of the given color is in check.
     * @param kingColor The color of the king in question.
     * @return True, if the king in question is under attack by the opponent.
     */
    public boolean isKingUnderAttack(PieceColor kingColor) {
        Position kingPosition = null;
        PieceColor opponentColor = invertColor(kingColor);
        Position[] p = null;

        //find the king
        for (int i = 0; i < BOARD_RANKS; i++)
            for (int j = 0; j < BOARD_FILES; j++)
                if (board[i][j] != null
                        && board[i][j].getPieceColor() == kingColor
                        && board[i][j] instanceof King)
                    kingPosition = Position.generateFromRankAndFile(i, j);

        p = getAllDestinationsByColor(opponentColor);

        for (int i = 0; i < p.length; i++)
            if (p[i].equals(kingPosition))
                return true;

        return false;
    }

    /**
     * A method that accumulates every square that can be reached by every piece of the given
     * color.
     *
     * @param color the given color
     * @return An array with all reachable squares by all pieces of a color
     */
    public Position[] getAllDestinationsByColor(PieceColor color) {
        ArrayList<Position> result = new ArrayList<>();

        for (int i = 0; i < BOARD_RANKS; i++)
            for (int j = 0; j < BOARD_FILES; j++)
                if (board[i][j] != null && board[i][j].getPieceColor() == color) {
                    Position[] current = board[i][j].allDestinations(this,
                            Position.generateFromRankAndFile(i, j), false);

                    for (Position currentPosition : current) {
                        if (!result.contains(currentPosition))
                            result.add(currentPosition);
                    }
                }

        return result.toArray(new Position[]{});
    }

    /**
     * Determines if a given color still has legal moves available.
     *
     * @param color the color to check
     * @return true if the color has legal moves, false otherwise
     */
    private boolean stillHasMoves(PieceColor color) {
        if (!isKingUnderAttack(color)) return true;

        boolean wouldStillBeInDangerAfterMove;

        for (int i = 0; i < BOARD_RANKS; i++) {
            for (int j = 0; j < BOARD_FILES; j++) {
                Piece currentPiece = this.board[i][j];
                if (currentPiece != null && currentPiece.getPieceColor() == color) {
                    Position[] possiblePositions = currentPiece.allDestinations(this,
                            Position.generateFromRankAndFile(i, j), false);
                    for (int k = 0; k < possiblePositions.length; k++) {
                        Piece[][] boardBackup = this.getBoard();

                        this.board[possiblePositions[k].getRank()][possiblePositions[k].getFile()]
                                = this.board[i][j];
                        this.board[i][j] = null;
                        wouldStillBeInDangerAfterMove = isKingUnderAttack(color);

                        this.board = boardBackup;
                        if (!wouldStillBeInDangerAfterMove)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Updates the game status based on the current state of the board.
     */
    private void updateGameStatus() {
        if (this.getTurn() == PieceColor.WHITE) {
            if (!stillHasMoves(PieceColor.WHITE)) {
                if (isKingUnderAttack(PieceColor.WHITE)) {
                    this.gameStatus = GameStatus.BLACK_WON;
                    return;
                }
                this.gameStatus = GameStatus.DRAW;
                return;
            }
        } else {
            if (!stillHasMoves(PieceColor.BLACK)) {
                if (isKingUnderAttack(PieceColor.BLACK)) {
                    this.gameStatus = GameStatus.WHITE_WON;
                    return;
                }
                this.gameStatus = GameStatus.DRAW;
                return;
            }
        }
    }

    /**
     * Inverts the given piece color.
     *
     * @param color the color to invert
     * @return the inverted color
     */
    public PieceColor invertColor(PieceColor color) {
        if (color == PieceColor.WHITE)
            return PieceColor.BLACK;
        return PieceColor.WHITE;
    }

    /**
     * A method to verify that
     * 1. The length of s must be 64.
     * 2. There must be exactly one white and one black king on the board.
     *
     * @param s the arrangement string
     * @throws IllegalArrangementException if the length of s is not 64
     * @throws InvalidNumberOfKingsException if there are more than one king of each color
     */
    public static void verifyArrangement(String s) throws IllegalArrangementException {
        boolean whiteKingPresent = false, blackKingPresent = false;
        if (s.length() != 64)
            throw new IllegalArrangementException("The length of the arrangement must be 64");
        for (int i = 0; i < 64; i++) {
            if (s.charAt(i) == 'K' || s.charAt(i) == 'L')
                if (!whiteKingPresent)
                    whiteKingPresent = true;
                else
                    throw new InvalidNumberOfKingsException();
            else if (s.charAt(i) == 'k' || s.charAt(i) == 'l')
                if (!blackKingPresent)
                    blackKingPresent = true;
                else
                    throw new InvalidNumberOfKingsException();
        }
        if (!whiteKingPresent || !blackKingPresent)
            throw new InvalidNumberOfKingsException();
    }

    /**
     * Creates and returns a deep copy of the Chess object.
     * The method performs a deep copy of the Chess object by cloning its board variable,
     * ensuring that the resulting clone is independent of the original object.
     *
     * @return A deep copy of the Chess object.
     */
    @Override
    public Chess clone() throws CloneNotSupportedException{
            Chess clone = (Chess) super.clone();
            clone.board = this.getBoard();
            return clone;
    }
}

