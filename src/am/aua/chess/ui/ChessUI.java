package am.aua.chess.ui;

import am.aua.chess.core.*;
import am.aua.chess.exceptions.IllegalArrangementException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The ChessUI class represents the graphical user interface for the chess game.
 * It extends JFrame to create the main window of the application.
 */
public class ChessUI extends JFrame {
    private Chess game;
    private BoardSquare[][] buttonReferences;
    private ArrayList<Position> gatheredClicks;

    /**
     * Constructs a ChessUI object, initializes the chess game, and sets up the GUI.
     *
     * @throws IllegalArrangementException if an illegal arrangement of chess pieces is detected
     */
    public ChessUI() {
        game = null;

        try {
            game = new Chess();
        } catch (Exception e) {}
        buttonReferences = new BoardSquare[Chess.BOARD_RANKS][Chess.BOARD_FILES];
        gatheredClicks = new ArrayList<>();

        this.setSize(900, 900);
        this.setTitle("Chess");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        JPanel board = new JPanel();
        board.setLayout(new GridLayout(Chess.BOARD_RANKS, Chess.BOARD_FILES));
        board.setSize(800, 800);

        for (int i = 0; i < Chess.BOARD_RANKS; i++) {
            for (int j = 0; j < Chess.BOARD_FILES; j++) {
                BoardSquare sq = new BoardSquare((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1), i, j);
                sq.setPreferredSize(new Dimension(100, 100));
                sq.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BoardSquare source = (BoardSquare) e.getSource();
                        int[] boardCoords = source.getCoordinates();
                        boardClicked(boardCoords);
                    }
                });
                buttonReferences[i][j] = sq;
                board.add(sq);
            }
        }

        this.add(board);
        updatePieces();
        this.setVisible(true);
    }

    /**
     * Handles the action when a board square is clicked.
     *
     * @param coords the coordinates of the clicked square
     */
    private void boardClicked(int[] coords) {
        if (gatheredClicks.size() == 0) {
            Position p1 = Position.generateFromRankAndFile(coords[0], coords[1]);

            if (game.getPieceAt(p1) == null || game.getPieceAt(p1).getPieceColor() != game.getTurn())
                //ensure the player starts from their piece
                return;

            Position[] highlights = game.reachableFrom(p1);

            for (int i = 0; i < highlights.length; i++)
                buttonReferences[highlights[i].getRank()][highlights[i].getFile()].setHighlight(true);

            //gather the starting square
            gatheredClicks.add(p1);
        } else {
            Position p2 = Position.generateFromRankAndFile(coords[0], coords[1]);
            //gatheredClicks.add(coords); //destination of the move
            gatheredClicks.add(p2);

            //We have enough clicks to try to make a move
            //game.move(gatheredClicks.get(0),gatheredClicks.get(1));
            game.performMove(new Move(gatheredClicks.get(0),gatheredClicks.get(1)));

            //discard all clicks and refresh the board
            gatheredClicks.clear();
            updatePieces();

            for (int i = 0; i < buttonReferences.length; i++)
                for (int j = 0; j < buttonReferences[i].length; j++)
                    buttonReferences[i][j].setHighlight(false);

            if (game.getGameStatus() == Chess.GameStatus.WHITE_WON) {
                JOptionPane.showMessageDialog(this, "White is victorious");
                this.dispose();
            } else if (game.getGameStatus() == Chess.GameStatus.BLACK_WON) {
                JOptionPane.showMessageDialog(this, "Black is victorious");
                this.dispose();
            } else if (game.getGameStatus() == Chess.GameStatus.DRAW) {
                JOptionPane.showMessageDialog(this, "Draw");
                this.dispose();
            }
        }
    }

    /**
     * Updates the GUI with the current state of the chess game.
     */
    private void updatePieces() {
        String s;
        Piece p;
        for (int i = 0; i < Chess.BOARD_RANKS; i++) {
            for (int j = 0; j < Chess.BOARD_FILES; j++) {
                p = game.getPieceAt(Position.generateFromRankAndFile(i, j));
                if (p != null)
                    buttonReferences[i][j].setPiece(p.toString()); //piece picture
                else
                    buttonReferences[i][j].setPiece(); //blank
            }
        }
    }
}
