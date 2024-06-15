package am.aua.chess.ui;

import javax.swing.*;
import java.awt.*;

/**
 * The BoardSquare class represents a single square on the chessboard GUI.
 * It extends JButton to enable clickable functionality.
 */
public class BoardSquare extends JButton {
    // Constant for the light color of the square.
    private static Color light = Color.LIGHT_GRAY;

    // Constant for the dark color of the square.
    private static Color dark = Color.decode("#1B9700");

    private int x; // x-coordinate of the square on the board
    private int y; // y-coordinate of the square on the board
    private Color color; // color of the square

    /**
     * Constructs a BoardSquare with the specified color and coordinates.
     *
     * @param isWhite indicates whether the square is light or dark
     * @param x the x-coordinate of the square on the board
     * @param y the y-coordinate of the square on the board
     */
    public BoardSquare(boolean isWhite, int x, int y) {
        super();
        this.color = isWhite ? light : dark;
        this.x = x;
        this.y = y;
        this.setBackground(color);
    }

    /**
     * Returns the coordinates of the square.
     *
     * @return an array containing the x and y coordinates of the square
     */
    public int[] getCoordinates() {
        return new int[] {x, y};
    }

    /**
     * Sets the piece icon on the square based on the provided piece name.
     *
     * @param s the name of the piece (e.g. "K"/"L" are for white kings, while "p" is for black pawn)
     */
    public void setPiece(String s) {
        switch (s) {
            case "K":
            case "L":
                this.setIcon(new ImageIcon("./src/am/aua/chess/ui/gfx/KingW.png"));
                break;
            case "Q":
                this.setIcon(new ImageIcon("./src/am/aua/chess/ui/gfx/QueenW.png"));
                break;
            case "R":
            case "S":
                this.setIcon(new ImageIcon("./src/am/aua/chess/ui/gfx/RookW.png"));
                break;
            case "N":
                this.setIcon(new ImageIcon("./src/am/aua/chess/ui/gfx/KnightW.png"));
                break;
            case "B":
                this.setIcon(new ImageIcon("./src/am/aua/chess/ui/gfx/BishopW.png"));
                break;
            case "P":
                this.setIcon(new ImageIcon("./src/am/aua/chess/ui/gfx/PawnW.png"));
                break;
            case "k":
            case "l":
                this.setIcon(new ImageIcon("./src/am/aua/chess/ui/gfx/KingB.png"));
                break;
            case "q":
                this.setIcon(new ImageIcon("./src/am/aua/chess/ui/gfx/QueenB.png"));
                break;
            case "r":
            case "s":
                this.setIcon(new ImageIcon("./src/am/aua/chess/ui/gfx/RookB.png"));
                break;
            case "n":
                this.setIcon(new ImageIcon("./src/am/aua/chess/ui/gfx/KnightB.png"));
                break;
            case "b":
                this.setIcon(new ImageIcon("./src/am/aua/chess/ui/gfx/BishopB.png"));
                break;
            case "p":
                this.setIcon(new ImageIcon("./src/am/aua/chess/ui/gfx/PawnB.png"));
                break;
        }
    }

    /**
     * Removes the piece icon from the square.
     */
    public void setPiece() {
        this.setIcon(null);
    }

    /**
     * Sets the highlight color of the square to red.
     *
     * @param highlighted true to highlight the square, false otherwise
     */
    public void setHighlight(boolean highlighted) {
        if (highlighted) this.setBackground(Color.RED);
            else this.setBackground(color);
    }
}

